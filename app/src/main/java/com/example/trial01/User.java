package com.example.trial01;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("img")
    private String img;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + first_name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public User(int id, String username, String first_name, String img) {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
