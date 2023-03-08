package com.example.mybinance.entity;

public class AddCurrencyRequest {

    private int walletId;
    private int currencyId;
    private double payload;

    public AddCurrencyRequest(int walletId, int currencyId, double payload) {
        this.walletId = walletId;
        this.currencyId = currencyId;
        this.payload = payload;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public double getPayload() {
        return payload;
    }

    public void setPayload(double payload) {
        this.payload = payload;
    }
}
