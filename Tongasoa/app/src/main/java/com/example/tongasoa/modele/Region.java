package com.example.tongasoa.modele;

import com.google.gson.annotations.SerializedName;

public class Region {

    public static final String _id = "id";
    public static final String _name = "name";
    public static final String _idProvince = "idProvince";

    @SerializedName("id")
    private String id;

    @SerializedName("idProvince")
    private String idProvince;

    @SerializedName("name")
    private String name;

    /**
     * Default constructor Region
     */
    public Region() {}

    /**
     * Constructor Region
     * @param id
     * @param idProvince
     * @param name
     */
    public Region(String id, String idProvince, String name) {
        this.id = id;
        this.idProvince = idProvince;
        this.name = name;
    }
}
