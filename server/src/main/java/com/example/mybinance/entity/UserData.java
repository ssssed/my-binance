package com.example.mybinance.entity;

public class UserData{

    private int id;
    private String username;
    private String avatar;
    private int walletId;

    public UserData(int id, String username, String avatar, int walletId) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.walletId = walletId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }
}
