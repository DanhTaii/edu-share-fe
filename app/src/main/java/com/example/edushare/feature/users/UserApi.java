package com.example.edushare.feature.users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @POST("api/users/register")
    Call<User> registerUser(@Body User user);

    @GET("api/users/{id}")
    Call<User> getUserProfile(@Path("id") String userId);


}

