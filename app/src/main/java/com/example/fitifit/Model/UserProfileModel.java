package com.example.fitifit.Model;

public class UserProfileModel {
    private String name, email, image , uid;

    public UserProfileModel() {
    }

    public UserProfileModel(String name, String email, String image, String uid) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
