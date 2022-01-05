package com.dinhtrongdat.socialmedia.model;

import java.io.Serializable;

public class User implements Serializable {
    String name, email, pass, avatar, cover, about;

    public User() {
    }

    public User(String name, String email, String pass, String avatar, String cover, String about) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.avatar = avatar;
        this.cover = cover;
        this.about = about;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
