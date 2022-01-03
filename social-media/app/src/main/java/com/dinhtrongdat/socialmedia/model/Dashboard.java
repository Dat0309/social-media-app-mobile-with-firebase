package com.dinhtrongdat.socialmedia.model;

import java.io.Serializable;

public class Dashboard implements Serializable {

    int profile, postImage;
    String name, status, like, comment, time;

    public Dashboard() {
    }

    public Dashboard(int profile, int postImage, String name, String status, String like, String comment, String time) {
        this.profile = profile;
        this.postImage = postImage;
        this.name = name;
        this.status = status;
        this.like = like;
        this.comment = comment;
        this.time = time;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
