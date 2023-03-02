package com.example.mybinance.entity;

public class UserEntity extends UserRequest {

    private Integer id;
    private String avatar;
    private String createdAt;
    private String updatedAt;

    public UserEntity(Integer id, String username, String password, String avatar, String createdAt, String updatedAt) {
        super(username, password);
        this.id = id;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
