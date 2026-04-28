package com.example.edushare.feature.users;

import androidx.annotation.NonNull;

import com.example.edushare.common.base.OnActionCallback;

import com.example.edushare.common.base.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final UserApi userApi;

    public UserRepository() {
        this.userApi = RetrofitClient.createService(UserApi.class);
    }

    public void register(User user, OnActionCallback callback) {
        userApi.registerUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("Đăng ký thành công dưới DB!");
                } else {
                    callback.onFailure("Lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                callback.onFailure("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
