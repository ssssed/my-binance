package com.example.mybinance.service;

import com.example.mybinance.entity.ConvertEntity;
import com.example.mybinance.error.ApiError;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ConvertService {

    public ConvertEntity convert(String from, String to) throws ApiError {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://api.binance.com/api/v3/ticker/price?symbol="+ from + to)
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);
            double price = jsonObject.getDouble("price");
            return new ConvertEntity(from, to, price);
        } catch (Exception err) {
            throw new ApiError("Что-то пошло не так!");
        }
    }


}
