package com.daklod.techshop.controller;


import android.util.Log;

import com.daklod.techshop.DTO.CUSTOMER;
import com.daklod.techshop.LoginActivity;
import com.daklod.techshop.R;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
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
    public static  CUSTOMER user;
    interface RequestLogin{
        @POST("/api/v1/login")
        Call<CUSTOMER> login(@Body LoginBody body);
    }
    public LoginAPI(){
        super();
    }

    public CUSTOMER postLogin(LoginBody body) throws Exception{
        return postLogin(body, false);
    }
    public CUSTOMER postLogin(LoginBody body, boolean isReFetch) throws Exception{
        if (user != null && isReFetch){
            Log.d(TAG, "getUser: test");
            return user;
        }
        RequestLogin requestCategory = retrofit.create(RequestLogin.class);

        Response<CUSTOMER> response = requestCategory.login(body).execute();
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
