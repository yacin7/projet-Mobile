package com.example.interfaceblog;
public class Item {

    String name;
    String description,date;
    int image;
    String like;
    String dislike;

    public Item(String name, String description, String date, int image, String like, String dislike) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.image = image;
        this.like = like;
        this.dislike = dislike;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDislike() {
        return dislike;
    }

    public void setDislike(String dislike) {
        this.dislike = dislike;
    }
}