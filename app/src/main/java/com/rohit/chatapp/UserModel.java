package com.rohit.chatapp;

public class UserModel {

    String Username;
    String img;

    public UserModel(String username, String img) {
        Username = username;
        this.img = img;
    }

    public UserModel(){};

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
