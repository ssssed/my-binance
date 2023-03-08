package com.example.mybinance.service;

import com.example.mybinance.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Currency create(String name, String symbol) {
        String query = "INSERT INTO public.currencies (name, symbol, \"createdAt\", \"updatedAt\") VALUES (?, ?, now(), now()) RETURNING id, name, symbol, \"createdAt\", \"updatedAt\"";
        return jdbcTemplate.queryForObject(query,
                new Object[]{name, symbol},
                (rs, rowNum) -> new Currency(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("symbol"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt")
                )
        );
    }


    public void addCurrencyToWallet(int walletId, int currencyId, double value) {
        String query = "INSERT INTO public.wallet_currencies (value, \"createdAt\", \"updatedAt\", \"walletId\", \"currencyId\") " +
                "VALUES (?, now(), now(), ?, ?) " +
                "ON CONFLICT (\"walletId\", \"currencyId\") DO UPDATE " +
                "SET value = public.wallet_currencies.value + ?";

        jdbcTemplate.update(query, value, walletId, currencyId, value);
    }

}
