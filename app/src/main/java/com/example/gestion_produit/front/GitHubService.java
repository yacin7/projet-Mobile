package com.example.gestion_produit.front;

import com.example.gestion_produit.model.Blog;
import com.example.gestion_produit.model.Event;
import com.example.gestion_produit.model.Products;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("/getProduit")
    Call<List<Products>> getAll();

    @POST("/incrementLike/{productId}")
    Call<Void> incrementLike(@Path("productId") int productId);

    @POST("/decrementLike/{productId}")
    Call<Void> decrementLike(@Path("productId") int productId);
    @GET("/getBlog")
    Call<List<Blog>> getAllblog();

    @POST("/addDescription")
    Call<AddDescriptionResponse> addDescription(@Body Blog request);

    @POST("/incrementLikeblog/{blogId}")
    Call<Void> incrementLikeblog(@Path("blogId") int blogId);

    @POST("/decrementLikeblog/{blogId}")
    Call<Void> decrementLikeblog(@Path("blogId") int blogId);

    @POST("/incrementDislikeblog/{blogId}")
    Call<Void> incrementDislike(@Path("blogId") int blogId);

    @POST("/decrementDislikeblog/{blogId}")
    Call<Void> decrementDislike(@Path("blogId") int blogId);
    @Multipart
    @POST("api/signup")
    Call<ResponseBody> signUp(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("birthdate") RequestBody birthdate,
            @Part("email") RequestBody email,
            @Part("height") RequestBody height,
            @Part("weight") RequestBody weight,
            @Part MultipartBody.Part profilePicture
    );

    @FormUrlEncoded
    @POST("api/signin")
    Call<ResponseBody> signIn(
            @Field("username") String username,
            @Field("password") String password
    );

    @Multipart
    @PUT("api/updateProfile/{userId}")
    Call<ResponseBody> updateProfile(
            @Part("username") RequestBody username,
            @Part("birthdate") RequestBody birthdate,
            @Part("email") RequestBody email,
            @Part("height") RequestBody height,
            @Part("weight") RequestBody weight,
            @Part MultipartBody.Part profilePicture,
            @Part("userId") RequestBody userId
    );

    @DELETE("api/deleteProfile/{userId}")
    Call<ResponseBody> deleteProfile(
            @Field("userId") String userId
    );

    @FormUrlEncoded
    @POST("api/updatePassword")
    Call<ResponseBody> updatePassword(
            @Field("email") String verificationCode,
            @Field("newPassword") String newPassword
    );

    @FormUrlEncoded
    @POST("api/displayCode")
    Call<ResponseBody> sendCode(
            @Field("email") String email,
            @Field("code") String code
    );
    @GET("/getevent")
    Call<List<Event>> getAllevent();

    @POST("/incrementParticipe/{eventID}")
    android.telecom.Call incrementParticipe(@Path("eventID") int eventID);

    @POST("/decrementParticipe/{eventID}")
    Call<Void> decrementParticipe(@Path("eventID") int eventID);
}
