package com.rajendra.techshop.DTO;

import java.sql.Date;

public class USER {
    String username;
    String password;
    Date date_created;
    boolean status;
    int role_id;
    int user_id;

    public USER(String username, String password, Date date_created, boolean status, int role_id, int user_id) {
        this.username = username;
        this.password = password;
        this.date_created = date_created;
        this.status = status;
        this.role_id = role_id;
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
