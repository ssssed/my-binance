package com.example.mybinance.entity;

public class ConvertRequest {
    private String to;
    private String from;

    public ConvertRequest(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}