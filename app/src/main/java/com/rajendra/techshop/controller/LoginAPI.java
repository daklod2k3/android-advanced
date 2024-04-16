package com.rajendra.techshop.controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.rajendra.techshop.DTO.USER;
import com.rajendra.techshop.LoginActivity;
import com.rajendra.techshop.R;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;


public class LoginAPI extends Api{

    public static class LoginBody {
        String username;
        String password;

        public LoginBody(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    String TAG = "Login Request";
    USER user;
    interface RequestLogin{
        @POST("/api/v1/login")
        Call<USER> login(@Body LoginBody body);
    }
    public LoginAPI(){
        super();
    }

    public USER postLogin(LoginBody body) throws Exception{
        return postLogin(body, false);
    }
    public USER postLogin(LoginBody body, boolean isReFetch) throws Exception{
        if (user != null && isReFetch){
            Log.d(TAG, "getUser: test");
            return user;
        }
        RequestLogin requestCategory = retrofit.create(RequestLogin.class);

        Response<USER> response = requestCategory.login(body).execute();
        user = response.body();
        Log.d(TAG, "postLogin: " + response.code());
        if (response.code() != 200)
            return null;
//            Log.d("request", getUser().get(0).toString());

//        Log.d(TAG, "postLogin: " + response.headers().get("Authorization"));
        String token = response.headers().get("authorization");
        Log.d(TAG, "postLogin: " + token);

        CheckAuthAPI.setToken(LoginActivity.getAppContext(), token);
        return user;

    }

//    public void ()

}
