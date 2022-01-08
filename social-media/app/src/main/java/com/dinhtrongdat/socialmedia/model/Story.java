package com.dinhtrongdat.socialmedia.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Story implements Serializable {
    private String storyBy;
    private long storyAt;
    ArrayList<UserStoriees> stories;

    public Story() {
    }

    public String getStoryBy() {
        return storyBy;
    }

    public void setStoryBy(String storyBy) {
        this.storyBy = storyBy;
    }

    public long getStoryAt() {
        return storyAt;
    }

    public void setStoryAt(long storyAt) {
        this.storyAt = storyAt;
    }

    public ArrayList<UserStoriees> getStories() {
        return stories;
    }

    public void setStories(ArrayList<UserStoriees> stories) {
        this.stories = stories;
    }

    public Story(String storyBy, long storyAt, ArrayList<UserStoriees> stories) {
        this.storyBy = storyBy;
        this.storyAt = storyAt;
        this.stories = stories;
    }
}
