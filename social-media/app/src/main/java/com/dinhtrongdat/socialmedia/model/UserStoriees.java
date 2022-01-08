package com.dinhtrongdat.socialmedia.model;

import java.io.Serializable;

public class UserStoriees implements Serializable {
    private String image;
    private long storyAt;

    public UserStoriees() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getStoryAt() {
        return storyAt;
    }

    public void setStoryAt(long storyAt) {
        this.storyAt = storyAt;
    }

    public UserStoriees(String image, long storyAt) {
        this.image = image;
        this.storyAt = storyAt;
    }
}
