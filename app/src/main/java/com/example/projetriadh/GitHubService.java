package com.example.projetriadh;

import com.example.projetriadh.model.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface GitHubService {
        @GET("/getevent")
        Call<List<Event>> getAll();

        @POST("/incrementParticipe/{eventID}")
        android.telecom.Call incrementParticipe(@Path("eventID") int eventID);

        @POST("/decrementParticipe/{eventID}")
        Call<Void> decrementParticipe(@Path("eventID") int eventID);

    }



