package com.rajendra.techshop.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rajendra.techshop.DTO.CATEGORY;
import com.rajendra.techshop.DTO.PRODUCT;
import com.rajendra.techshop.DTO.USER;
import com.rajendra.techshop.LoginActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.rajendra.techshop.R.drawable.*;

public class ProductAPI extends Api{
    List<PRODUCT> productList;
    PRODUCT product;
    public static class addCartBody {
        int product_id, amount;

        public addCartBody(int product_id, int amount) {
            this.product_id = product_id;
            this.amount = amount;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
    interface ProductRequest{
        @GET("/api/v1/product")
        Call<List<PRODUCT>> getProduct();

        @GET("/api/v1/product")
        Call<List<PRODUCT>> getProductByID(@Query("filter") String filter);

        @POST("/api/v1/cart")
        Call<PRODUCT> addProductToCart(@Body addCartBody body);
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
            Log.d("request", productList.get(0).toString());
            return productList;
        } catch (Exception e){
            Log.e(TAG, "getProduct: ", e);

            return productList;
        }
    }

    public PRODUCT addProductToCart(addCartBody body) throws IOException {
        ProductRequest requestProduct = retrofit.create(ProductRequest.class);

        try {
            Response<PRODUCT> response = requestProduct.addProductToCart(body).execute();

            if (response.isSuccessful()) {
                product = response.body();
                Log.d(TAG, "postAddCart: Success");
                return product;
            } else {
                Log.e(TAG, "postAddCart: Failed with code " + response.code());
                return null;
            }
        } catch (IOException e) {
            Log.e(TAG, "postAddCart: Error", e);
            throw e;
        }
    }


}
