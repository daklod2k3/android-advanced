package com.rajendra.techshop.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rajendra.techshop.DTO.CATEGORY;
import com.rajendra.techshop.DTO.PRODUCT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.rajendra.techshop.R.drawable.*;

public class ProductAPI extends Api{
    List<PRODUCT> productList;
    PRODUCT product;
    interface ProductRequest{
        @GET("/api/v1/product")
        Call<List<PRODUCT>> getProduct();

        @GET("/api/v1/product?filter=product:eq:{product_id}")
        Call<PRODUCT> getProductByID(@Path("product_id") String product_id);
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
            Log.e(TAG, "getProduct: ", e);

            return productList;
        }
    }
    public PRODUCT getProductByID(String id) {
        ProductRequest productRequest = retrofit.create(ProductRequest.class);
        try {
            Response<PRODUCT> response = productRequest.getProductByID(id).execute();
            product = response.body();
            Log.d("request", product.toString());
            return product;
        } catch (Exception e){
            Log.e(TAG, "getProduct: ", e);

            return product;
        }
    }


}
