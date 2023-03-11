package com.example.mybinance.contoller;

import com.example.mybinance.entity.UserEntity;
import com.example.mybinance.entity.UserRequest;
import com.example.mybinance.error.ApiError;
import com.example.mybinance.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody UserRequest user) throws ApiError {
        try {
            return ResponseEntity.ok(adminService.login(user.getUsername(), user.getPassword()));
        } catch (ApiError err) {
            return ResponseEntity.badRequest().body(err);
        }
    }

    @PostMapping("/create")
    public ResponseEntity register(@RequestBody UserRequest user, @RequestHeader("Authorization") String authHeader) throws ApiError {
        try {
            adminService.create(user.getUsername(), user.getPassword(), authHeader);
            return ResponseEntity.ok("Пользователь создан!");
        } catch (ApiError err) {
            return ResponseEntity.badRequest().body(err.toString());
        }
    }

    @GetMapping("/users")
    public ResponseEntity getUsersList(@RequestHeader("Authorization") String authHeader) {
        try {
            return ResponseEntity.ok(adminService.getUsers(authHeader));
        } catch (ApiError e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity getTransactions(@RequestHeader("Authorization") String authHeader) {
        try {
            return ResponseEntity.ok(adminService.getTransfers(authHeader));
        } catch (ApiError e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity deleteUser(@RequestHeader("Authorization") String authHeader, @PathVariable int id) {
        try {
            return ResponseEntity.ok(adminService.deleteUser(authHeader, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла какая-то ошибка");
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable int id, @RequestBody UserEntity user) {
        try {
            adminService.updateUserInfo(user.getUsername(), user.getPassword(), user.getAvatar(), id);
            return ResponseEntity.ok("Пользователь " + user.getUsername() + " обновлен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }
}
