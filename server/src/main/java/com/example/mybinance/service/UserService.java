package com.example.mybinance.service;

import com.example.mybinance.entity.Currency;
import com.example.mybinance.entity.UserData;
import com.example.mybinance.entity.UserEntity;
import com.example.mybinance.error.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
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

//    public void register(String username, String password) throws ApiError {
//        if (isUserCreated(username)) {
//            throw new ApiError("Такой пользователь с таким именем уже существует!");
//        }
//        String sql = "INSERT INTO users(username, password, avatar) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql, username, password, null);
//    }

    public UserData registerUser(String username, String password, String avatar, String walletName) {
        // Insert new user into 'users' table
        jdbcTemplate.update("INSERT INTO public.users (username, password, avatar) VALUES (?, ?, ?)", username, password, avatar);

        // Get the ID of the newly created user
        Integer userId = jdbcTemplate.queryForObject("SELECT id FROM public.users WHERE username = ?", new Object[]{username}, Integer.class);

        // Insert new wallet into 'wallets' table
        jdbcTemplate.update("INSERT INTO public.wallets (name, \"userId\", \"createdAt\", \"updatedAt\") VALUES (?, ?, NOW(), NOW())", walletName, userId);

        // Get the ID of the newly created wallet
        Integer walletId = jdbcTemplate.queryForObject("SELECT id FROM public.wallets WHERE name = ?", new Object[]{walletName}, Integer.class);

        // Insert new wallet currency into 'wallet_currencies' table for each currency
        List<Currency> currencies = getAllCurrencies(); // assume this method retrieves all available currencies
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
        Integer walletId = jdbcTemplate.queryForObject("SELECT id FROM public.wallets WHERE name = ?", new Object[]{user.getUsername() + "Wallet"}, Integer.class);
        return new UserData(user.getId(), user.getUsername(), user.getAvatar(), walletId);
    }
}
