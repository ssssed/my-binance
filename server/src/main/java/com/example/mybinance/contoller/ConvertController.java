package com.example.mybinance.contoller;

import com.example.mybinance.entity.ConvertEntity;
import com.example.mybinance.entity.ConvertRequest;
import com.example.mybinance.error.ApiError;
import com.example.mybinance.service.ConvertService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/convert")
public class ConvertController {

    @Autowired
    private ConvertService convertService;


    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    public ResponseEntity convert(@RequestBody ConvertRequest body) throws ApiError {
        try {
            ConvertEntity answer = convertService.convert(body.getFrom(), body.getTo());
            return ResponseEntity.ok(answer);
        } catch (ApiError error) {
            return ResponseEntity.badRequest().body(error);
        }
    }
}
