package com.rajendra.techshop.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rajendra.techshop.DTO.CATEGORY;
import com.rajendra.techshop.DTO.PRODUCT;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import static com.rajendra.techshop.R.drawable.*;

public class ProductAPI extends Api{
    List<PRODUCT> productList;
    interface ProductRequest{
        @GET("/api/v1/product")
        Call<List<PRODUCT>> getProduct();
    }
    public ProductAPI(){
        super();
        productList = new ArrayList<>();
    }

    public List<PRODUCT> getProduct(){
        return getProduct( false);
    }
    public List<PRODUCT> getProduct(boolean isReFetch){
        if (productList.size() > 0 && isReFetch)
            return productList;
        ProductRequest productRequest = retrofit.create(ProductRequest.class);
        try {
            Response<List<PRODUCT>> response = productRequest.getProduct().execute();
            productList = response.body();
            Log.d("request", productList.get(0).toString());
            return productList;
        } catch (Exception e){
//            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            Log.d("request", e.toString());
//            categoryList.add(new CATEGORY(1, ic_laptop_windows, "Máy tính"));
//            categoryList.add(new CATEGORY(3, ic_devices_other, "Phụ kiệndsfaasdfasdf"));
//            categoryList.add(new CATEGORY(2, ic_smartphone, "Điện thoại"));
            return productList;
        }
    }

//    public void ()

}
