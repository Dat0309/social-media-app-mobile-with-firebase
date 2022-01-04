package com.dinhtrongdat.socialmedia.model;

import java.io.Serializable;

public class Notification implements Serializable {

    int profile;
    String name, notification, time;

    public Notification() {
    }

    public Notification(int profile, String name, String notification, String time) {
        this.profile = profile;
        this.name = name;
        this.notification = notification;
        this.time = time;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
