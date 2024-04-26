package com.rajendra.techshop.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.rajendra.techshop.LoginActivity;
import com.rajendra.techshop.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class CheckAuthAPI extends Api{
    final String TAG = "Check Auth";
    interface RequestCheckAuth {
        @GET("/api/v1/check-auth")
        Call<Void> checkAuth();
    }

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
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.auth_key_stored), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.auth_key_stored), token);
        editor.apply();
        setAuthHeader(token);
    }


}