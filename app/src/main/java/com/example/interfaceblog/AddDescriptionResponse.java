package com.example.interfaceblog;

import com.google.gson.annotations.SerializedName;

public class AddDescriptionResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
