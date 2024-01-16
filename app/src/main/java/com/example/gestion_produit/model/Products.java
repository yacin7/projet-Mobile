package com.example.gestion_produit.model;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Products implements Serializable {
    @PrimaryKey(autoGenerate = true)
    Integer productId;
    @ColumnInfo
    @SerializedName("productName")
    String productName;
    @ColumnInfo
    @SerializedName("productPrice")
  int productPrice;
    @ColumnInfo
    @SerializedName("imageurl")
   String imageurl;
    @ColumnInfo
    @SerializedName("numberinCart")

     int numberinCart;
    @ColumnInfo
    @SerializedName("description")

     String description;
    @ColumnInfo
    @SerializedName("like")
    int like;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Products(Integer productId, String productName, int productPrice, String imageurl, int like) {
        this.productId= productId;
        this.productName = productName;

        this.productPrice = productPrice;
        this.imageurl = imageurl;
        this.like=like;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public Products(Integer productId, String productName, int productPrice, String imageurl, String description,int like) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageurl = imageurl;
        this.description = description;
        this.like=like;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getNumberinCart() {
        return numberinCart;
    }

    public void setNumberinCart(int numberinCart) {
        this.numberinCart = numberinCart;
    }
    public void incrementLike() {
        like++;
    }

    public void decrementLike() {
        if (like > 0) {
            like--;
        }
    }
}
