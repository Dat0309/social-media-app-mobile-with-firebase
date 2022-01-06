package com.dinhtrongdat.socialmedia.model;

import java.io.Serializable;

public class Follow implements Serializable {

    private String followedBy;
    private long followedAt;

    public Follow() {

    }

    public Follow(String followedBy, long followedAt) {
        this.followedBy = followedBy;
        this.followedAt = followedAt;
    }

    public String getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(String followedBy) {
        this.followedBy = followedBy;
    }

    public long getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(long followedAt) {
        this.followedAt = followedAt;
    }
}
