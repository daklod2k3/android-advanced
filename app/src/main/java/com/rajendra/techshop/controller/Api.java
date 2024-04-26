package com.rajendra.techshop.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rajendra.techshop.DTO.CATEGORY;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Api {

    String TAG = "API class";

    Retrofit retrofit;
    public static OkHttpClient client;

//    String url = "https://daklod-backend.vercel.app";
    public static String url = "http://192.168.194.180:3000";

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
