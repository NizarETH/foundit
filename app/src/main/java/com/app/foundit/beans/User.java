package com.app.foundit.beans;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {


    @PrimaryKey
    private String id;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private String password;
    private String picture;
    private boolean isAdmin;
    private RealmList<MyObject> myObjects;

    public User() {
    }

    public User(String id, String first_name,String last_name, String phone, String email, String password, String picture) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public RealmList<MyObject> getMyObjects() {
        return myObjects;
    }

    public void setMyObjects(RealmList<MyObject> myObjects) {
        this.myObjects = myObjects;
    }
}
