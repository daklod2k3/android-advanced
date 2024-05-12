package com.daklod.techshop.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.daklod.techshop.DTO.CUSTOMER;
import com.daklod.techshop.DTO.USER;
import com.daklod.techshop.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public class CheckAuthAPI extends Api{
    static final String TAG = "Check Auth";
    interface RequestCheckAuth {
        @GET("/api/v1/check-auth")
        Call<Void> checkAuth();
    }
    public static String token;

    public CheckAuthAPI(){
        super();
    }

    public boolean checkAuth() throws Exception{
        RequestCheckAuth requestCheckAuth = retrofit.create(RequestCheckAuth.class);
        Response<Void> response = requestCheckAuth.checkAuth().execute();
        Log.d(TAG, "checkAuth: " + response.code());
        return response.isSuccessful();
    }

    public static void clearToken(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.auth_key_stored), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

    public static void setAuthHeader(String token){
        // Add token to retrofit header
        CheckAuthAPI.token = token;
        OkHttpClient.Builder clientHeader = new OkHttpClient.Builder();
        clientHeader.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request origin = chain.request();

                Request request = origin.newBuilder()
                        .header("authorization", token)
                        .method(origin.method(), origin.body())
                        .build();


                return chain.proceed(request);
            }
        });
        Api.client = clientHeader.build();
    }

    public static void setToken(Context context, String token){
        CheckAuthAPI.token = token;
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.auth_key_stored), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.auth_key_stored), token);
        editor.apply();
        setAuthHeader(token);
    }

    public static CUSTOMER getUserFromToken(){
        Gson gson = new Gson();
        String body = token.split("\\.")[1];
        Log.d(TAG, "getUserFromToken: " + body);
        return gson.fromJson(getJson(token.split("\\.")[1]), CUSTOMER.class);
    }

    public static String getJson(String encode){
        byte[] decodedBytes = Base64.decode(encode, Base64.URL_SAFE);
        try {
            return new String(decodedBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}