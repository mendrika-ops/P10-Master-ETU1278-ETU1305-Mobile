package com.example.tongasoa.modele;

import com.google.gson.annotations.SerializedName;

public class Favoris {
    public static final String _id = "id";
    public static final String _idUser = "idUser";
    public static final String _idSite = "idSite";
    public static final String _description = "description";

    @SerializedName("id")
    private String id;

    @SerializedName("idUser")
    private String idUser;

    @SerializedName("idSite")
    private String idSite;

    @SerializedName("description")
    private String description;

    @SerializedName("etat")
    private int etat;

    @SerializedName("createDate")
    private String createDate;


    public Favoris() {
    }

    public Favoris(String id, String idUser, String idSite, String description, int etat, String createDate) {
        this.id = id;
        this.idUser = idUser;
        this.idSite = idSite;
        this.description = description;
        this.etat = etat;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdSite() {
        return idSite;
    }

    public void setIdSite(String idSite) {
        this.idSite = idSite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getNbreReaction(){
        return "";
    }
}
