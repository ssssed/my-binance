package com.example.mybinance.contoller;

import com.example.mybinance.entity.TradeRequest;
import com.example.mybinance.entity.UserEntity;
import com.example.mybinance.entity.UserRequest;
import com.example.mybinance.error.ApiError;
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
            return ResponseEntity.ok(userService.registerUser(user.getUsername(), user.getPassword(), null, user.getUsername() + "Wallet"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Пользователь не был создан");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable int id, @RequestBody UserEntity user) {
        try {
            userService.updateUserInfo(user.getUsername(), user.getPassword(), user.getAvatar(), id);
            return ResponseEntity.ok("Пользователь " + user.getUsername() + " обновлен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity getWallets(@PathVariable int id) {
        try {
            return ResponseEntity.ok(userService.getWallets(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Что-то пошло не так");
        }
    }

    @PatchMapping("/trade")
    public ResponseEntity trade(@RequestBody TradeRequest trade) {
        try {
            userService.tradeWallet(trade.getWalletId(), trade.getSymbol(), trade.getValue());
            return ResponseEntity.ok("успех!");
        } catch (ApiError error) {
            return ResponseEntity.badRequest().body("У вас недостаточно средств!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Транзакция не прошла!");
        }
    }
}
