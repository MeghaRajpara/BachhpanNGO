package com.megha.finalproject.Entities;

public class LoginResponse {

    private int user_id;
    private String volunteer_email;
    private String username;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return volunteer_email;
    }

    public void setEmail(String volunteer_email) {
        this.volunteer_email = volunteer_email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
