package com.megha.finalproject.Entities;

public class LoginResponse {

    private int user_id;
    private String volunteer_email;
    private String first_name;
    private String last_name;
    private String username;
    private Boolean status;

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

    public void setUsername(String username) {
        this.username = this.getFirst_name() + " " + this.getLast_name();
    }

    public String getUsername() {
        return this.getFirst_name() + " " + this.getLast_name();
    }


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
