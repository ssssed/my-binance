package com.example.mybinance.service;

import com.example.mybinance.entity.TransactionEntity;
import com.example.mybinance.entity.UserData;
import com.example.mybinance.entity.UserEntity;
import com.example.mybinance.error.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean isUserCreated(String username) {
        String sql = "SELECT COUNT(*) FROM admins WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, username) != 0;
    }

    private UserEntity getUser(String username, String password) {
        return jdbcTemplate.query(
                "SELECT * FROM admins WHERE username = ? and password = ?",
                (rs, rowNum) -> new UserEntity(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("avatar")
                ), username, password
        ).get(0);
    }

    public UserEntity login(String username, String password) throws ApiError {
        String sql = "SELECT COUNT(*) FROM admins WHERE username = ? AND password = ?";
        if (jdbcTemplate.queryForObject(sql, Integer.class, username, password) == 1) {
            return getUser(username, password);
        } else
            throw new ApiError("Неправильный логин или пароль");
    }

    public UserData getUserInfo(UserEntity user) {
        String sql = "SELECT id FROM wallets WHERE \"userId\" = ?";
        Integer walletId = jdbcTemplate.queryForObject(sql, new Object[]{user.getId()}, Integer.class);
        return new UserData(user.getId(), user.getUsername(), user.getAvatar(), walletId);
    }

    public void create(String name, String password, String authHeader) throws ApiError {
        if (authHeader.equals(name + ":" + password)) {
            throw new ApiError("У вас нет прав администратора!");
        }
        if (isUserCreated(name)) {
            throw new ApiError("Администратор с таким именем существует");
        }
        String sql = "INSERT INTO admins(username, password, avatar) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, name, password, null);
    }

    public List<UserEntity> getUsers(String auth) throws ApiError {
        List<String> adminData = List.of(auth.split(":"));
        UserEntity admin = getUser(adminData.get(0), adminData.get(1));
        if (admin != null) {
            String selectAll = "SELECT * FROM users";
            return jdbcTemplate.query(selectAll, (rs, rowNum) -> new UserEntity(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("avatar"))
            );
        }
        throw new ApiError("у вас недостаточно прав!");
    }

    public List<TransactionEntity> getTransfers(String auth) throws ApiError {
        List<String> adminData = List.of(auth.split(":"));
        UserEntity admin = getUser(adminData.get(0), adminData.get(1));
        if (admin != null) {
            String selectAll = "SELECT * FROM transactions";
            List<TransactionEntity> transactionEntities = jdbcTemplate.query(selectAll, (rs, rowNum) -> new TransactionEntity(
                    rs.getLong("id"),
                    rs.getDouble("amount"),
                    rs.getDouble("price"),
                    rs.getString("type"),
                    rs.getTimestamp("createdAt"),
                    rs.getTimestamp("updatedAt"),
                    rs.getLong("walletId"),
                    rs.getLong("currencyId")
            ));
            String addSymbol = "SELECT symbol FROM currencies WHERE id = ?";
            for (TransactionEntity transaction : transactionEntities) {
                String symbol = jdbcTemplate.queryForObject(addSymbol, String.class, transaction.getCurrencyId());
                transaction.setSymbol(symbol);
            }
            return  transactionEntities;
        }
        throw new ApiError("у вас недостаточно прав!");
    }

    public List<UserEntity> deleteUser(String authToken, int id) throws ApiError {
        List<String> authParse = List.of(authToken.split(":"));
        UserEntity admin = login(authParse.get(0), authParse.get(1));
        if (admin == null) {
            throw new ApiError("У вас недостаточно прав");
        }
        String sql = "DELETE FROM public.users WHERE id = ?";
        jdbcTemplate.update(sql, id);
        try {
            return getUsers(authToken);
        } catch (ApiError e) {
            throw new ApiError(e.getMessage());
        }
    }

    public void updateUserInfo(String name, String password, String avatar, int id) {
        String sql = "UPDATE public.users SET username = ?, password = ?, avatar = ? WHERE id = ?";
        String query = "UPDATE public.wallets SET name = ? updatedAt = now() WHERE id = ?";
        jdbcTemplate.update(sql, name, password, avatar, id);
        UserData user = getUserInfo(new UserEntity(id, name, password, avatar));
        jdbcTemplate.update(query, user.getUsername() + "Wallet", user.getWalletId());
    }
}
