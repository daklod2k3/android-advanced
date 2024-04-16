package com.rajendra.techshop.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.rajendra.techshop.LoginActivity;
import com.rajendra.techshop.R;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.POST;

public class CheckAuthAPI extends Api{
    interface RequestCheckAuth {
        @POST("/api/v1/check-auth")
        Call<Void> checkAuth();
    }

    public boolean checkAuth() throws Exception{
        RequestCheckAuth requestCheckAuth = retrofit.create(RequestCheckAuth.class);
        Response<Void> response = requestCheckAuth.checkAuth().execute();
        return response.isSuccessful();
    }

    public static void clearToken(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.auth_key_stored), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

    public static void setToken(Context context, String token){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.auth_key_stored), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.auth_key_stored), token);
        editor.apply();
    }


}