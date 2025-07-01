package com.example.post;

public class Post {
    private int id;
    private String title;

    public Post(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String toString() {
        return id + ". " + title;
    }
}

