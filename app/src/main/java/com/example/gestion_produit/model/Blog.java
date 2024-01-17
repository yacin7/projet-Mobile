package com.example.gestion_produit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Blog {

    @PrimaryKey(autoGenerate = true)
    int id ;
    @ColumnInfo
    @SerializedName("name")
    String name;
    @ColumnInfo
    @SerializedName("description")
    String description;
    @ColumnInfo
    @SerializedName("date")
    String date;
    @ColumnInfo
    int image;
    @ColumnInfo
    @SerializedName("like")
    int like;
    @ColumnInfo
    @SerializedName("dislike")
    int dislike;

    public Blog(){

    }

    public Blog(String name, String description, String date, int image, int like, int dislike) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.image = image;
        this.like = like;
        this.dislike = dislike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }
    // Ajoutez la méthode incrementDislike
    public void incrementLike() {
        like++;
    }

    public void decrementLike() {
        if (like > 0) {
            like--;
        }
    }

    public void incrementDislike() {
        dislike++;
    }

    public void decrementDislike() {
        if (dislike > 0) {
            dislike--;
        }
    }
}
