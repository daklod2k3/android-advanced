package com.rajendra.techshop.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rajendra.techshop.DTO.CATEGORY;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Api {

    Retrofit retrofit;
    public static OkHttpClient client;

//    String url = "https://daklod-backend.vercel.app/";
    String url = "http://192.168.194.180:3000";


    public Api(){

        if (client != null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        else
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    }
}
