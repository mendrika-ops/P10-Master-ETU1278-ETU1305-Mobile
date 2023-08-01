package com.example.tongasoa.modele;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

/**
 * Class Model Site
 */
public class Site {
    public static final String _id = "id";
    public static final String _idRegion = "idRegion";
    public static final String _idCategory = "idCategory";
    public static final String _name = "name";
    public static final String _description = "description";
    public static final String _link = "link";
    public static final String _Region = "region";
    public static final String _Category = "category";

    @SerializedName("id")
    private String id;

    @SerializedName("idRegion")
    private String idRegion;

    @SerializedName("idCategory")
    private String idCategory;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("link")
    private String link;

    @SerializedName("createdDate")
    private Date createdDate;

    @SerializedName("Region")
    private Region region;

    @SerializedName("Category")
    private Category category;

    /**
     * Constructor empty for Site
     */
    public Site() {
    }

    /**
     * Constructor for type generic
     * @param id
     * @param idRegion
     * @param idCategory
     * @param name
     * @param description
     * @param link
     * @param createdDate
     */
    public Site(String id, String idRegion, String idCategory, String name, String description, String link, Date createdDate) {
        this.id = id;
        this.idRegion = idRegion;
        this.idCategory = idCategory;
        this.name = name;
        this.description = description;
        this.link = link;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(String idRegion) {
        this.idRegion = idRegion;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
