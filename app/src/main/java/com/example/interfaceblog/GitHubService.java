package com.example.interfaceblog;

import com.example.interfaceblog.entities.Blog;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("/getBlog")
    Call<List<Blog>> getAll();

    @POST("/addDescription")
    Call<AddDescriptionResponse> addDescription(@Body Blog request);

    @POST("/incrementLike/{blogId}")
    Call<Void> incrementLike(@Path("blogId") int blogId);

    @POST("/decrementLike/{blogId}")
    Call<Void> decrementLike(@Path("blogId") int blogId);

    @POST("/incrementDislike/{blogId}")
    Call<Void> incrementDislike(@Path("blogId") int blogId);

    @POST("/decrementDislike/{blogId}")
    Call<Void> decrementDislike(@Path("blogId") int blogId);
}

