package com.dinhtrongdat.socialmedia.model;

import java.io.Serializable;

public class Post implements Serializable {
    private String postId;
    private String postImage;
    private String postedBy;
    private String posrDescription;
    private long postedAt;
    private long postLike;
    private long commentCount;

    public Post() {
    }

    public Post(String postId, String postImage, String postedBy, String posrDescription, long postedAt, long postLike, long commentCount) {
        this.postId = postId;
        this.postImage = postImage;
        this.postedBy = postedBy;
        this.posrDescription = posrDescription;
        this.postedAt = postedAt;
        this.postLike = postLike;
        this.commentCount = commentCount;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPosrDescription() {
        return posrDescription;
    }

    public void setPosrDescription(String posrDescription) {
        this.posrDescription = posrDescription;
    }

    public long getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(long postedAt) {
        this.postedAt = postedAt;
    }

    public long getPostLike() {
        return postLike;
    }

    public void setPostLike(long postLike) {
        this.postLike = postLike;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }
}
