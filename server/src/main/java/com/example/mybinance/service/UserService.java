package com.example.mybinance.service;

import com.example.mybinance.entity.*;
import com.example.mybinance.error.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean isUserCreated(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, username) != 0;
    }

    private UserEntity getUser(String username, String password) {
        return jdbcTemplate.query(
                "SELECT * FROM users WHERE username = ? and password = ?",
                (rs, rowNum) -> new UserEntity(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("avatar")
                ), username, password
        ).get(0);
    }

    public UserData auth(String username, String password) throws ApiError {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        if (jdbcTemplate.queryForObject(sql, Integer.class, username, password) == 1) {
            final UserEntity user = getUser(username, password);
            return getUserInfo(user);
        } else
            throw new ApiError("Неправильный логин или пароль");
    }

    public UserData registerUser(String username, String password, String avatar, String walletName) {

        jdbcTemplate.update("INSERT INTO public.users (username, password, avatar) VALUES (?, ?, ?)", username, password, avatar);

        Integer userId = jdbcTemplate.queryForObject("SELECT id FROM public.users WHERE username = ?", new Object[]{username}, Integer.class);

        jdbcTemplate.update("INSERT INTO public.wallets (name, \"userId\", \"createdAt\", \"updatedAt\") VALUES (?, ?, NOW(), NOW())", walletName, userId);

        Integer walletId = jdbcTemplate.queryForObject("SELECT id FROM public.wallets WHERE name = ?", new Object[]{walletName}, Integer.class);

        List<Currency> currencies = getAllCurrencies();
        for (Currency currency : currencies) {
            jdbcTemplate.update("INSERT INTO public.wallet_currencies (\"walletId\", \"currencyId\", \"value\", \"createdAt\", \"updatedAt\") VALUES (?, ?, 0, NOW(), NOW())", walletId, currency.getId());
        }
        return new UserData(userId, username, avatar, walletId);
    }

    private List<Currency> getAllCurrencies() {
        return jdbcTemplate.query("SELECT * FROM public.currencies", new CurrencyMapper());
    }

    private static class CurrencyMapper implements RowMapper<Currency> {
        public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
            Currency currency = new Currency(rs.getLong("id"), rs.getString("name"), rs.getString("symbol"), rs.getTimestamp("createdAt"), rs.getTimestamp("updatedAt"));
            currency.setId(rs.getLong("id"));
            currency.setName(rs.getString("name"));
            currency.setSymbol(rs.getString("symbol"));
            currency.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            currency.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
            return currency;
        }
    }

    public UserData getUserInfo(UserEntity user) {
        String sql = "SELECT id FROM wallets WHERE \"userId\" = ?";
        Integer walletId = jdbcTemplate.queryForObject(sql, new Object[]{user.getId()}, Integer.class);
        return new UserData(user.getId(), user.getUsername(), user.getAvatar(), walletId);
    }

    public void updateUserInfo(String name, String password, String avatar, int id) {
        String sql = "UPDATE public.users SET username = ?, password = ?, avatar = ? WHERE id = ?";
        String query = "UPDATE public.wallets SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, password, avatar, id);
        UserData user = getUserInfo(new UserEntity(id, name, password, avatar));
        jdbcTemplate.update(query, name + "Wallet", user.getWalletId());
    }

    public List<Wallet> getWallets(int walletId) {
        String query = "SELECT * FROM public.wallet_currencies WHERE \"walletId\" = ?";
        String sql = "SELECT * FROM public.currencies WHERE id = ?";
//        Получение всех валют с их количеством
        List<Wallet> wallets = jdbcTemplate.query(query, (rs, rowNum) -> new Wallet(
                rs.getInt("walletId"),
                rs.getInt("currencyId"),
                rs.getDouble("value")
        ), walletId);
// расшифровка каждой валюты
        for (Wallet wallet : wallets) {
            Currency currency = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                            new Currency(
                                    rs.getLong("id"),
                                    rs.getString("name"),
                                    rs.getString("symbol"),
                                    rs.getTimestamp("createdAt"),
                                    rs.getTimestamp("updatedAt"))
                    , wallet.getCurrencyId());
            wallet.setName(currency.getName());
            wallet.setSymbol(currency.getSymbol());
        }
        return wallets;
    }

    public void addTransaction(double amount, double price, String type, int walletId, int currencyId) {
        String sql = "INSERT INTO transactions (amount, price, \"createdAt\", \"updatedAt\", \"walletId\", \"currencyId\", type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        LocalDateTime now = LocalDateTime.now(); // получаем текущую дату и время
        jdbcTemplate.update(sql, amount, price, Timestamp.valueOf(now), Timestamp.valueOf(now), walletId, currencyId, type);
    }

    public void tradeWallet(int walletId, String currencySymbol, double value) throws ApiError {
        String query = "SELECT * FROM public.wallet_currencies WHERE \"walletId\" = ? AND \"currencyId\" = ?";
        String sql = "SELECT id FROM currencies WHERE symbol = ?";

        Integer currencyId = jdbcTemplate.queryForObject(sql, Integer.class, currencySymbol);
        String type;
        List<Wallet> wallets = jdbcTemplate.query(query, (rs, rowNum) -> new Wallet(
                rs.getInt("walletId"),
                rs.getInt("currencyId"),
                rs.getDouble("value")
        ), walletId, currencyId);
        Wallet wallet;
//        если кошелька с такой валютой нет, создаем его и выполняем операции дальше
        if (wallets.isEmpty()) {
            jdbcTemplate.update("INSERT INTO public.wallet_currencies (value, \"createdAt\", \"updatedAt\", \"walletId\", \"currencyId\") " +
                    "VALUES (?, now(), now(), ?, ?)", 0.0, walletId, currencyId);
            wallets = jdbcTemplate.query(query, (rs, rowNum) -> new Wallet(
                    rs.getInt("walletId"),
                    rs.getInt("currencyId"),
                    rs.getDouble("value")
            ), walletId, currencyId);
        }
        wallet = wallets.get(0);

        if (value < 0) {
            type = "SELL";
            if (wallet.getValue() + value < 0) throw new ApiError("у вас недостаточно средств");
        } else type = "BUY";

        double sumWithPercent = wallet.getValue() + (value * 0.1);
        wallet.setValue(sumWithPercent);

        String updateQuery = "UPDATE wallet_currencies SET value = ? WHERE \"currencyId\" = ? AND \"walletId\" = ?";
        jdbcTemplate.update(updateQuery, wallet.getValue(), currencyId, walletId);

        addTransaction(value, value + (value * 0.1), type, walletId, currencyId);
    }
}
