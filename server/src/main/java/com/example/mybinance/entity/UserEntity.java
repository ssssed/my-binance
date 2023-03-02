package com.example.mybinance.entity;

public class UserEntity extends UserRequest {

    private Integer id;
    private String avatar;

    public UserEntity(Integer id, String username, String password, String avatar) {
        super(username, password);
        this.id = id;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
