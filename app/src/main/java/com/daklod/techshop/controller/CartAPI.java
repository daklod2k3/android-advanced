package com.daklod.techshop.controller;

import android.util.Log;

import com.daklod.techshop.DTO.CATEGORY;
import com.daklod.techshop.DTO.INVOICE_DETAIL;
import com.daklod.techshop.DTO.PRODUCT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static com.daklod.techshop.R.drawable.*;

public class CartAPI extends Api{

    interface RequestCart{
        @GET("/api/v1/cart")
        Call<GetCartResponse> getCart();

        @POST("/api/v1/cart")
        Call<Void> addCart(@Body AddCartBody body);
    }
    public static class AddCartBody {
        int product_id;
        int amount;
        public AddCartBody(int product_id, int amount){
            this.product_id = product_id;
            this.amount = amount;
        }
    }

    RequestCart requestCart;

    public static class GetCartResponse {
        List<PRODUCT> productList;
        List<INVOICE_DETAIL> invoiceDetail;

        public GetCartResponse(List<PRODUCT> productList, List<INVOICE_DETAIL> invoiceDetail) {
            this.productList = productList;
            this.invoiceDetail = invoiceDetail;
        }

        public List<PRODUCT> getProductList() {
            return productList;
        }

        public void setProductList(List<PRODUCT> productList) {
            this.productList = productList;
        }

        public List<INVOICE_DETAIL> getInvoiceDetail() {
            return invoiceDetail;
        }

        public void setInvoiceDetail(List<INVOICE_DETAIL> invoiceDetail) {
            this.invoiceDetail = invoiceDetail;
        }
    }

    public CartAPI(){
        requestCart = retrofit.create(RequestCart.class);
    }

    public Response<GetCartResponse> getCart() throws IOException{
        Response<GetCartResponse> response = requestCart.getCart().execute();
        return response;
    }

    public Response<Void> addCart(AddCartBody body) throws IOException {
        return  requestCart.addCart(body).execute();
    }

//    public void ()

}
