package com.example.mybinance.contoller;

import com.example.mybinance.entity.UserEntity;
import com.example.mybinance.entity.UserRequest;
import com.example.mybinance.error.ApiError;
import com.example.mybinance.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
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
}
