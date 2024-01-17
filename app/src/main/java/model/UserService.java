package model;

import android.graphics.Bitmap;

import Classes.User;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import okhttp3.MultipartBody;
import retrofit2.http.Query;

public interface UserService {
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
}
