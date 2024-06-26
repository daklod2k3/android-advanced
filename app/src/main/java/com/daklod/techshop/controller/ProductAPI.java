package com.daklod.techshop.controller;

import android.util.Log;

import com.daklod.techshop.DTO.PRODUCT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ProductAPI extends Api{
    List<PRODUCT> productList;
    PRODUCT product;

    interface ProductRequest{
        @GET("/api/v1/product")
        Call<List<PRODUCT>> getProduct();

        @GET("/api/v1/product")
        Call<List<PRODUCT>> getProductByID(@Query("filter") String filter);



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
    public List<PRODUCT> getProductByID(int id) {

        String filter = "product_id:eq:" + id;
        ProductRequest productRequest = retrofit.create(ProductRequest.class);
        try {
            Response<List<PRODUCT>> response = productRequest.getProductByID(filter).execute();
            productList = response.body();
            Log.d("request", response.body().toString());
            return productList;
        } catch (Exception e){
            Log.e(TAG, "getProduct: ", e);

            return productList;
        }
    }


    public List<PRODUCT> getProductBySearch(String s) {
        ProductRequest productRequest = retrofit.create(ProductRequest.class);
        try {
            Response<List<PRODUCT>> response = productRequest.getProduct().execute();
            productList = response.body();
            List<PRODUCT> newList = new ArrayList<>();
            for (PRODUCT element : productList) {
                if(element.getName().toLowerCase().contains(s.toLowerCase())) {
                    newList.add(element);
                }
            }
            return newList;
        } catch (Exception e){
            Log.e(TAG, "getProduct: ", e);
            return productList;
        }
    }


    public Response<List<PRODUCT>> getProductByInvoice(int id) throws IOException{
        return retrofit.create(ProductRequest.class).getProductByID("invoice_id:eq:" + id).execute();
    }

}
