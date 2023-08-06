package com.example.tongasoa.modele;

import com.google.gson.annotations.SerializedName;

public class Category {

    public static final String _id = "id";
    public static final String _name = "name";

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    /**
     * Default constructor Category
     */
    public Category() {}

    /**
     * Constructor Category
     * @param id
     * @param name
     */
    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
