package com.daklod.techshop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daklod.techshop.DTO.INVOICE_DETAIL;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.R;
import com.daklod.techshop.adapter.ThanhToanAdapter;
import com.daklod.techshop.controller.CartAPI;
import com.daklod.techshop.controller.InvoiceAPI;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import retrofit2.Response;

public class thanhtoanActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView subTotal,promotion,total,address;
    Button btnThanhToan;
    ImageView btnBack;
    View rootView;
    List<PRODUCT> productList = new ArrayList<PRODUCT>();
    List<INVOICE_DETAIL> details = new ArrayList<INVOICE_DETAIL>();
    int tongTien=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);

        recyclerView = findViewById(R.id.cartView);




        rootView = findViewById(R.id.rootView);
        subTotal = findViewById(R.id.subTotalTxt);
        promotion = findViewById(R.id.promotionTxt);
        total = findViewById(R.id.totalTxt);
        address = findViewById(R.id.addressTxt);
        btnThanhToan = findViewById(R.id.btnOrderNow);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi ImageView được nhấn
                onBackPressed();
            }
        });
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
            tongTien += productList.get(i).getPrice()*details.get(i).getAmount();
        }
        NumberFormat money = NumberFormat.getCurrencyInstance();
        money.setMaximumFractionDigits(0);
        money.setCurrency(Currency.getInstance("VND"));

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        promotion.setText("0đ");

        subTotal.setText(formatter.format(tongTien) + "đ");
        total.setText(formatter.format(tongTien) + "đ");
        ThanhToanAdapter adapter = new ThanhToanAdapter(this,productList,details);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new payTask().execute();
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


        public void onBackPressed() {
            finish();
        }

        @Override
        protected void onPostExecute(CartAPI.GetCartResponse getCartResponse) {
            setLoad(false);
            productList = getCartResponse.getProductList();
            details = getCartResponse.getInvoiceDetail();
            loadGiaoDien(productList,details);
        }
    }
    protected void setLoad(boolean b) {
        if(!b){
            rootView.setVisibility(View.VISIBLE);
            return;
        }
        rootView.setVisibility(View.GONE);
    }
    class payTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                return new InvoiceAPI().pay();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(Void unused) {
            Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}