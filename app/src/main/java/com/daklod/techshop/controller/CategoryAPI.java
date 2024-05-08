package com.daklod.techshop.controller;

import android.util.Log;

import com.daklod.techshop.DTO.CATEGORY;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import static com.daklod.techshop.R.drawable.*;

public class CategoryAPI extends Api {
    List<CATEGORY> categoryList;

    interface RequestCategory {
        @GET("/api/v1/category")
        Call<List<CATEGORY>> getCategory();
    }

    public CategoryAPI() {
        super();
        categoryList = new ArrayList<>();
    }

    public List<CATEGORY> getCategory() {
        return getCategory(false);
    }

    public List<CATEGORY> getCategory(boolean isReFetch) {
        if (categoryList.size() > 0 && isReFetch)
            return categoryList;
        RequestCategory requestCategory = retrofit.create(RequestCategory.class);
        try {
            Response<List<CATEGORY>> response = requestCategory.getCategory().execute();
            categoryList = response.body();
            Log.d("request", categoryList.get(0).toString());
            return categoryList;
        } catch (Exception e) {
            // Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            Log.d("request", e.toString());
            // categoryList.add(new CATEGORY(1, ic_laptop_windows, "Máy tính"));
            // categoryList.add(new CATEGORY(3, ic_devices_other, "Phụ kiệndsfaasdfasdf"));
            // categoryList.add(new CATEGORY(2, ic_smartphone, "Điện thoại"));
            return categoryList;
        }
    }

    // public void ()

}
