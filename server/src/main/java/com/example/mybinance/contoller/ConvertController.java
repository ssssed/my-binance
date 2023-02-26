package com.example.mybinance.contoller;

import com.example.mybinance.entity.ConvertRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/convert")
public class ConvertController {
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    public ResponseEntity convert(@RequestBody ConvertRequest body) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://api.binance.com/api/v3/ticker/price?symbol="+ body.getFrom() + body.getTo())
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);
            double price = jsonObject.getDouble("price");
            final Map<String,Double> result = new HashMap<>();
            result.put("price", price);
            return ResponseEntity.status(200).body(result);
        }
        catch (IOException err) {
            Map<String, String> error = new HashMap<>();
            error.put("message", err.toString());
            return ResponseEntity.status(400).body(error);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
