package com.example.tongasoa.modele;

import com.google.gson.annotations.SerializedName;

public class Commentaire {
    public static final String _id = "id";
    public static final String _idUser = "idUser";
    public static final String _idSite = "idSite";
    public static final String _commentaire = "commentaire";
    public static final String _note = "note";
    public static final String _createDate = "createDate";
    @SerializedName("id")
    private String id;

    @SerializedName("idUser")
    private String idUser;

    @SerializedName("idSite")
    private String idSite;

    @SerializedName("commentaire")
    private String commentaire;

    @SerializedName("note")
    private String note;

    @SerializedName("createDate")
    private String createDate;

    public Commentaire() {
    }

    public Commentaire(String commentaire, String note) {
        this.commentaire = commentaire;
        this.note = note;
    }

    public Commentaire(String commentaire, String note, String createDate) {
        this.commentaire = commentaire;
        this.note = note;
        this.createDate = createDate;
    }

    public Commentaire(String id, String idUser, String idSite, String commentaire, String note, String createDate) {
        this.id = id;
        this.idUser = idUser;
        this.idSite = idSite;
        this.commentaire = commentaire;
        this.note = note;
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

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
