package com.example.trial01.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public User(String id, String username, String password) {

        this.id = id;
        this.username = username;
        this.password = password;

    }

    public User(){

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getId() {

        return id;

    }

    public void setId(String id) {

        this.id = id;
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

}