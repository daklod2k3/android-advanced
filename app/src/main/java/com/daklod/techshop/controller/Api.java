package com.daklod.techshop.controller;

import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    String TAG = "API class";

    Retrofit retrofit;
    public static OkHttpClient client;

//    public static String url = "https://daklod-backend.vercel.app";
    public static String url = "http://10.0.2.2:3000";

    public static final int TimeOut = 15;

    public Api(){

        if (client != null){
            Log.d(TAG, "Api with header");
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        else{
            Log.d(TAG, "Api no header");
            OkHttpClient default_client = new OkHttpClient().newBuilder()
                    .connectTimeout(TimeOut, TimeUnit.SECONDS)
//                    .readTimeout(TimeOut, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(default_client)
                    .build();
        }

    }
}
