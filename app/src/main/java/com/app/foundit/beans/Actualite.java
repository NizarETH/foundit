package com.app.foundit.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Actualite extends RealmObject {

    @PrimaryKey
    private String id;
    private String data;
    private String category;
    private String date;

    public Actualite(String id, String data, String date) {
        this.id = id;
        this.data = data;
        this.date = date;
    }

    public Actualite() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
