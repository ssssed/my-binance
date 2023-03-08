package com.example.mybinance.contoller;

import com.example.mybinance.entity.AddCurrencyRequest;
import com.example.mybinance.entity.Currency;
import com.example.mybinance.entity.CurrencyRequest;
import com.example.mybinance.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;


    @PostMapping("/create")
    public ResponseEntity createCurrency(@RequestBody CurrencyRequest currency) {
        try {
            return ResponseEntity.ok(
                    currencyService.create(
                            currency.getName(),
                            currency.getSymbol()
                    ));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/add")
    public ResponseEntity addCurrency(@RequestBody AddCurrencyRequest request) {
        try {
            currencyService.addCurrencyToWallet(request.getWalletId(), request.getCurrencyId(), request.getPayload());
            return ResponseEntity.ok("Валюта была добавлена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Валюта не добавлена");
        }


    }
}
