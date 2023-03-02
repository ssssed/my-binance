package com.example.mybinance.contoller;

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
}
