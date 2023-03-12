package com.example.mybinance.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Currency {
    private Long id;
    private String name;
    private String symbol;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Currency(long id, String name, String symbol, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.createdAt = createdAt.toLocalDateTime();
        this.updatedAt = updatedAt.toLocalDateTime();
    }

    public Currency() {}

    public Currency(String name, String symbol, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.symbol = symbol;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

