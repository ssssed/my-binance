package com.example.mybinance.service;

import com.example.mybinance.entity.UserEntity;
import com.example.mybinance.entity.UserRequest;
import com.example.mybinance.error.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private boolean isUserCreated(String username) {
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
                        rs.getString("avatar"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt")
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
}
