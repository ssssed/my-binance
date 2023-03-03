package com.example.mybinance.contoller;

import com.example.mybinance.entity.UserRequest;
import com.example.mybinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody UserRequest user) {
        try {
            return ResponseEntity.ok(userService.auth(user.getUsername(), user.getPassword()));
        } catch (Exception err) {
            return ResponseEntity.badRequest().body("Такого пользователя не существует или вы ввели неправильные данные");
        }
    }

    @PostMapping("/create")
    public ResponseEntity register(@RequestBody UserRequest user) {

        try {
            userService.register(user.getUsername(), user.getPassword());
            return ResponseEntity.ok("Пользователь создан");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
