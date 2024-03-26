package com.rajendra.techshop.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rajendra.techshop.DTO.CATEGORY;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Api {

    Retrofit retrofit;


    public Api(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://20.5.227.28:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }
}
