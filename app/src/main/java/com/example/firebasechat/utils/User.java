package com.example.firebasechat.utils;

public class User {

    private String name;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
