package com.example.mybinance.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public ResponseEntity getTest() {
        try {
            final Map<String, String> response = new HashMap<>();
            response.put("message", "answer");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Упал я нахуй");
        }
    }

    @PostMapping
    public ResponseEntity sendData(@RequestBody Map<String, String> data) {
        try {
            String week = data.get("week");
            return ResponseEntity.ok().body(week.toUpperCase());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("я ебал");
        }
    }
}
