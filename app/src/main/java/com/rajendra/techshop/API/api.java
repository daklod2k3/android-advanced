package com.rajendra.techshop.API;

import android.util.Log;

import com.rajendra.techshop.DTO.CATEGORY;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Api {
    interface RequestCategory{
        @GET("/api/v1/category")
        Call<List<CATEGORY>> getCategory();
    }

    Retrofit retrofit;


    public Api(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://20.5.227.28:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    public void getCategory(){
        RequestCategory requestCategory = retrofit.create(RequestCategory.class);
        requestCategory.getCategory().enqueue(new Callback<List<CATEGORY>>() {
            @Override
            public void onResponse(Call<List<CATEGORY>> call, Response<List<CATEGORY>> response) {
                List<CATEGORY> categoryList = response.body();
                Log.d("request", categoryList.get(0).getName());
            }

            @Override
            public void onFailure(Call<List<CATEGORY>> call, Throwable t) {
                Log.e("request", t.toString());
            }
        });
    }
}
