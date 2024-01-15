package com.example.gestion_produit.front;

import com.example.gestion_produit.model.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("/getProduit")
    Call<List<Products>> getAll();

    @POST("/incrementLike/{productId}")
    Call<Void> incrementLike(@Path("productId") int productId);

    @POST("/decrementLike/{productId}")
    Call<Void> decrementLike(@Path("productId") int productId);
}
