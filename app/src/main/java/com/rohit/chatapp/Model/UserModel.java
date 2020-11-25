package com.rohit.chatapp.Model;

public class UserModel {

    String Username;
    String img;
    String uid;

    public UserModel(String username, String img, String uid) {
        Username = username;
        this.img = img;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
