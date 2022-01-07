package com.dinhtrongdat.socialmedia.model;

import java.io.Serializable;

public class Following implements Serializable {

    String following;
    long followedAt;

    public Following() {
    }

    public Following(String following, long followedAt) {
        this.following = following;
        this.followedAt = followedAt;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public long getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(long followedAt) {
        this.followedAt = followedAt;
    }
}
