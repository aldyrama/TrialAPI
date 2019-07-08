package com.example.trial01.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("username") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("first_name") String nama,
                                       @Field("username") String email,
                                       @Field("password") String password);

    @FormUrlEncoded
    @GET("profile")
    Call<ResponseBody> profile(@Field("id") int id,
                               @Field("first_name") String nama);

}
