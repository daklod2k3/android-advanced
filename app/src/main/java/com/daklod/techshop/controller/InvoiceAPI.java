package com.daklod.techshop.controller;

import android.util.Log;

import com.daklod.techshop.DTO.INVOICE;
import com.daklod.techshop.DTO.INVOICE_DETAIL;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.view.InvoiceDetail;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class InvoiceAPI extends Api {
    interface RequestInvoice{
        @POST("/api/v1/pay")
        Call<Void> payment();

        @GET("/api/v1/pay")
        Call<List<INVOICE>> getInvoice();

        @GET("/api/v1/pay")
        Call<GetInvoiceDetailResponse> getInvoiceById(@Query("filter") String filter);
    }


    public InvoiceAPI(){
        super();
    }
    public Void pay() throws Exception{

        InvoiceAPI.RequestInvoice requestPay = retrofit.create(InvoiceAPI.RequestInvoice.class);

        Response<Void> response = requestPay.payment().execute();

        if (response.isSuccessful()){
            Log.d(TAG, "thanh toan thanh cong ");
        }
        Log.d(TAG, "thanh toan that bai ");

        return null;
    }

    public Response<List<INVOICE>> getInvoice() throws IOException {
        return retrofit.create(RequestInvoice.class).getInvoice().execute();
    }

    public static class GetInvoiceDetailResponse {
        @SerializedName("invoice")
        INVOICE invoice;
        @SerializedName("productList")
        List<PRODUCT> productList;
        @SerializedName("invoiceDetail")
        List<INVOICE_DETAIL> invoiceDetail;


        public List<PRODUCT> getProductList() {
            return productList;
        }

        public List<INVOICE_DETAIL> getInvoiceDetail() {
            return invoiceDetail;
        }

        public INVOICE getInvoice() {
            return invoice;
        }
    }

    public Response<GetInvoiceDetailResponse> getInvoiceById(int id) throws IOException {
        return retrofit.create(RequestInvoice.class).getInvoiceById("invoice_id:eq:" + id).execute();
    }

}
