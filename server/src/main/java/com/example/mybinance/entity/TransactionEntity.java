package com.example.mybinance.entity;

import java.sql.Timestamp;

enum TransactionType {
    BUY,
    SELL
}

public class TransactionEntity {
    private Long id;
    private Double amount;
    private Double price;
    private TransactionType type;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long walletId;
    private Long currencyId;

    public TransactionEntity(Long id, Double amount, Double price, String type, Timestamp createdAt, Timestamp updatedAt, Long walletId, Long currencyId) {
        this.id = id;
        this.amount = amount;
        this.price = price;
        this.type = TransactionType.valueOf(type);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.walletId = walletId;
        this.currencyId = currencyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }
}

