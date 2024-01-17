package com.example.gestion_produit.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("birthdate")
    private String birthdate;

    @SerializedName("email")
    private String email;

    @SerializedName("height")
    private double height;

    @SerializedName("weight")
    private double weight;

    @SerializedName("profile_picture")
    private byte[] profilePicture;

    public User(int id, String username, String birthdate, String email, double height, double weight, byte[] profilePicture) {
        this.id = id;
        this.username = username;
        this.birthdate = birthdate;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.profilePicture = profilePicture;
    }

    public User() {
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture.getBytes();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", email='" + email + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }

    protected User(Parcel in) {
        id = in.readInt();
        username = in.readString();
        birthdate = in.readString();
        email = in.readString();
        height = in.readDouble();
        weight = in.readDouble();
        profilePicture = in.createByteArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(birthdate);
        dest.writeString(email);
        dest.writeDouble(height);
        dest.writeDouble(weight);
        dest.writeByteArray(profilePicture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
