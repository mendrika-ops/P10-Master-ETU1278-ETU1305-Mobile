package com.example.tongasoa.modele;

import com.google.gson.annotations.SerializedName;

public class User {

    public static final String _id = "id";
    public static final String _name = "name";
    public static final String _firstname = "firstname";
    public static final String _email = "email";
    public static final String _password = "password";

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    @SerializedName("firtName")
    private String firstName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public User() {}

    public User(String id, String name, String firstName, String email, String password) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





}
