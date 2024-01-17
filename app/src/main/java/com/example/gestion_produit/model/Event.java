package com.example.gestion_produit.model;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Event implements Serializable {

    private Bitmap bitmap;
    @PrimaryKey(autoGenerate = true)
            Integer eventID;
    @ColumnInfo
    @SerializedName("title")
    String title;
    @ColumnInfo
    @SerializedName("placeName")
    String placeName;
    @ColumnInfo
    @SerializedName("dateEvent")
    Date dateEvent;
    @ColumnInfo
    @SerializedName("imageURL")
    String imageURL;

    @ColumnInfo
    @SerializedName("Description")
    String Description;

    @ColumnInfo
    @SerializedName("nbrparticipe")
    Integer nbrparticipe;

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Integer getNbrparticipe() {
        return nbrparticipe;
    }

    public void setNbrparticipe(Integer nbrparticipe) {
        this.nbrparticipe = nbrparticipe;
    }

    public Event(Bitmap bitmap, Integer eventID, String title, String placeName, Date dateEvent, String imageURL, String description, Integer nbrparticipe) {
        this.bitmap = bitmap;
        this.eventID = eventID;
        this.title = title;
        this.placeName = placeName;
        this.dateEvent = dateEvent;
        this.imageURL = imageURL;
        Description = description;
        this.nbrparticipe = nbrparticipe;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Event(Integer eventID, String title, String placeName, Date dateEvent, String imageURL) {
        this.eventID = eventID;
        this.title = title;
        this.placeName = placeName;
        this.dateEvent = dateEvent;
        this.imageURL = imageURL;
    }

    public Event(String title, String placeName, Date dateEvent) {
        this.title = title;
        this.placeName = placeName;
        this.dateEvent = dateEvent;
    }

    public Event(String marathon, String carthage, String s, int marathon1) {

    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Event(Bitmap bitmap, Integer eventID, String title, String placeName, Date dateEvent, String imageURL, String description) {
        this.bitmap = bitmap;
        this.eventID = eventID;
        this.title = title;
        this.placeName = placeName;
        this.dateEvent = dateEvent;
        this.imageURL = imageURL;
        Description = description;
    }

    public Event(String title, String placeName, Date dateEvent, String imageURL) {
        this.title = title;
        this.placeName = placeName;
        this.dateEvent = dateEvent;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }

    public void incrementParticipe(){
        nbrparticipe++;
    }

    public void decrementParticipe(){
        if(nbrparticipe>0){
            nbrparticipe--;
        }
    }
}
