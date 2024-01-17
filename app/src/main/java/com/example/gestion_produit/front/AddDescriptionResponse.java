package com.example.gestion_produit.front;

import com.google.gson.annotations.SerializedName;

public class AddDescriptionResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
