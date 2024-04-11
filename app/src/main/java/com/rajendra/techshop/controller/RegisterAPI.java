package com.rajendra.techshop.controller;

import android.util.Log;
import android.widget.Toast;

import com.rajendra.techshop.DTO.CUSTOMER;
import com.rajendra.techshop.DTO.USER;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;


public class RegisterAPI extends Api{

    public static class RegisterBody {
        String username;
        String password;
        String email;
        String phone;
        String name;

        public RegisterBody(String name, String username, String email, String password, String phone) {
            this.name = name;
            this.username = username;
            this.email = email;
            this.password = password;
            this.phone = phone;
        }
    }

    String TAG = "Register Request";
    CUSTOMER customer;
    Response<CUSTOMER> response;
    interface RequestRegister{
        @POST("/api/v1/register")
        Call<CUSTOMER> register(@Body RegisterBody body);
    }
    public RegisterAPI(){
        super();
    }

    public Response<CUSTOMER> postRegister(RegisterBody body) throws Exception{
        return postRegister(body, false);
    }
    public Response<CUSTOMER> postRegister(RegisterBody body, boolean isReFetch) throws Exception{
        if (customer != null && isReFetch){
            Log.d(TAG, "getUser: test");
            return response;
        }
        RequestRegister requestCategory = retrofit.create(RequestRegister.class);

        response = requestCategory.register(body).execute();
        return response;
//        user = response.body();
//        Log.d(TAG, "postRegister: " + response.code());
//        if (response.code() != 200)
//            return null;
////            Log.d("request", getUser().get(0).toString());
//        return user;

    }

//    public void ()

}
