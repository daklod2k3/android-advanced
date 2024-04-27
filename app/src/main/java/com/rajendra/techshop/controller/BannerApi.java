package com.rajendra.techshop.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rajendra.techshop.DTO.BANNER;
import com.rajendra.techshop.DTO.CATEGORY;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import static com.rajendra.techshop.R.drawable.*;

public class BannerApi extends Api{
    String TAG = "Banner api";
    List<BANNER> bannerList;
    interface RequestCategory{
        @GET("/api/v1/banner")
        Call<List<BANNER>> getBanner();
    }
    public BannerApi(){
        super();
        bannerList = new ArrayList<>();
    }

    public List<BANNER> getBanner(){
        return getBanner( false);
    }
    public List<BANNER> getBanner(boolean isReFetch){
        if (bannerList.size() > 0 && isReFetch)
            return bannerList;
        RequestCategory requestCategory = retrofit.create(RequestCategory.class);
        try {
            Response<List<BANNER>> response = requestCategory.getBanner().execute();
            bannerList = response.body();
            Log.d(TAG, bannerList.get(0).toString());
            return bannerList;
        } catch (Exception e){
            Log.d(TAG, e.toString());
            return bannerList;
        }
    }

//    public void ()

}
