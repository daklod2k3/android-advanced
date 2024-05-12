package com.daklod.techshop.controller;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.POST;

public class InvoiceAPI extends Api {
    interface RequestInvoice{
        @POST("/api/v1/pay")
        Call<Void> payment();
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
}
