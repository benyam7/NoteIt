package com.example.insertactivity;

import java.io.Serializable;

public class Note implements Serializable
{
    String id;
    String description;
    String title;
    String date;
    String imageUrl;

    public Note(){}
    public Note(String description, String title, String date/* String imageUrl*/) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
