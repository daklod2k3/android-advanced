package com.rajendra.techshop.API;

import com.rajendra.techshop.DTO.USER;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class api {
    interface RequestCategory{
        @GET("/api/v1/category")
        Call<USER> getCategory();
    }

    public api(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:/localhost:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestCategory requestCategory = retrofit.create(RequestCategory.class);
        requestCategory.getCategory().enqueue(new Callback<USER>() {
            @Override
            public void onResponse(Call<USER> call, Response<USER> response) {

            }

            @Override
            public void onFailure(Call<USER> call, Throwable t) {

            }
        });
    }
}
