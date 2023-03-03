package com.example.mybinance.service;

import com.example.mybinance.entity.UserEntity;
import com.example.mybinance.error.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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

    public UserEntity auth(String username, String password) throws ApiError {

        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        if (jdbcTemplate.queryForObject(sql, Integer.class, username, password) == 1) {
            return getUser(username, password);
        } else
            throw new ApiError("Неправильный логин или пароль");
    }

    public void register(String username, String password) throws ApiError {
        if( isUserCreated(username) ) {
            throw new ApiError("Такой пользователь с таким именем уже существует!");
        }
        String sql = "INSERT INTO users(username, password, avatar) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, username, password, null);
    }
}
