package com.daklod.techshop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daklod.techshop.DTO.INVOICE_DETAIL;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.R;
import com.daklod.techshop.adapter.ThanhToanAdapter;
import com.daklod.techshop.controller.CartAPI;
import com.daklod.techshop.controller.InvoiceAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class thanhtoanActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView subTotal,totalTax,total,address;
    Button btnThanhToan;
    View rootView;
    List<PRODUCT> productList = new ArrayList<PRODUCT>();
    List<INVOICE_DETAIL> details = new ArrayList<INVOICE_DETAIL>();
    int tongTienChuaThue = 0;
    int thue = 0;
    int tongTien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);

        recyclerView = findViewById(R.id.cartView);




        rootView = findViewById(R.id.rootView);
        subTotal = findViewById(R.id.subTotalTxt);
        totalTax = findViewById(R.id.totalTaxTxt);
        total = findViewById(R.id.totalTxt);
        address = findViewById(R.id.addressTxt);
        btnThanhToan = findViewById(R.id.btnOrderNow);
        new LoadCartTask().execute();

    }
    public void loadGiaoDien(List<PRODUCT> mProductList,List<INVOICE_DETAIL> mDetails){
        productList = mProductList;
        details = mDetails;
        Bundle extras = getIntent().getExtras(); // Lấy Bundle từ Intent
        if (extras != null) {
            String value = extras.getString("address"); // Lấy giá trị chuỗi từ Bundle
            address.setText(value);

        }


        for (int i = 0; i < productList.size();i++){
            tongTienChuaThue += productList.get(i).getPrice()*details.get(i).getAmount();
            thue += details.get(i).getTax()*details.get(i).getAmount();
        }
        tongTien = tongTienChuaThue - thue;
        subTotal.setText(tongTienChuaThue+"");
        totalTax.setText(thue+"");
        total.setText(tongTien+"");
        ThanhToanAdapter adapter = new ThanhToanAdapter(this,productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new InvoiceAPI().pay();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    class LoadCartTask extends AsyncTask<Void, String, CartAPI.GetCartResponse> {

        public LoadCartTask(){

        }

        @Override
        protected CartAPI.GetCartResponse doInBackground(Void... voids) {
            try {
                Response<CartAPI.GetCartResponse> response = new CartAPI().getCart();
                if (response.isSuccessful()){
                    return response.body();
                }
                publishProgress(response.message());

            }catch (IOException e){
                publishProgress("Lỗi kết nối!");
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            setLoad(false);

        }

        protected void setLoad(boolean b) {
            if(!b){
                rootView.setVisibility(View.VISIBLE);
                return;
            }
            rootView.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(CartAPI.GetCartResponse getCartResponse) {
            setLoad(false);
            productList = getCartResponse.getProductList();
            details = getCartResponse.getInvoiceDetail();
            loadGiaoDien(productList,details);
        }
    }
}