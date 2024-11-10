package com.seller.sellerservice.DTOs;

public class AutherDTO {



    long imageID;
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    long Id;

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String nation) {
        About = nation;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    String About;

    String Language;
}
