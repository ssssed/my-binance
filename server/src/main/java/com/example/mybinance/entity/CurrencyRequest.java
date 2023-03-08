package com.example.mybinance.entity;

public class CurrencyRequest {

    private String name;
    private String symbol;

    public CurrencyRequest(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
