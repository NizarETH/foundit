package com.app.foundit.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MyObject extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private String location;
    private String date;
    private String details;
    private String category;
    private String picture;
    private User founder;

    public MyObject(String id, String name, String category, String picture) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.picture = picture;
    }

    public MyObject() {
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getFounder() {
        return founder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
