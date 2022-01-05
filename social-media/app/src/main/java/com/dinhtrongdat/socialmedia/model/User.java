package com.dinhtrongdat.socialmedia.model;

import java.io.Serializable;

public class User implements Serializable {
    String name, email, pass, avatar;

    public User() {
    }

    public User(String name, String email, String pass, String avatar) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
