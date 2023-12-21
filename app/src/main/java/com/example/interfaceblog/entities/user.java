package com.example.interfaceblog.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class user {
    @PrimaryKey(autoGenerate = true)
     int id;
    @ColumnInfo
    @SerializedName("username")
     String username;
    @ColumnInfo
    @SerializedName("image")
     int image;
    @ForeignKey(entity = Blog.class, parentColumns = "id", childColumns = "blogId")
     int blogId;

    public user(int id, String username) {
        this.id = id;
        this.username = username;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }
}
