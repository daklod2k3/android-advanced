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

//    public List getCategory(Context context){
//        RequestCategory requestCategory = retrofit.create(RequestCategory.class);
//        requestCategory.getCategory().enqueue(new Callback<List<CATEGORY>>() {
//            @Override
//            public void onResponse(Call<List<CATEGORY>> call, Response<List<CATEGORY>> response) {
//                List<CATEGORY> categoryList = response.body();
//                Toast.makeText(context, categoryList.get(0).getName(), Toast.LENGTH_LONG).show();
//                Log.d("request", categoryList.get(0).getName());
//            }
//
//            @Override
//            public void onFailure(Call<List<CATEGORY>> call, Throwable t) {
//                Toast.makeText( null, t.toString(), Toast.LENGTH_LONG).show();
//                Log.e("request", t.toString());
//            }
//        });
//        Log.d("void", "test");
//    }
}
