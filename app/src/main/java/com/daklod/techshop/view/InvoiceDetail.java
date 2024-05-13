package com.daklod.techshop.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daklod.techshop.DTO.INVOICE;
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

public class InvoiceDetail extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView subTotal,promotion,total,address;
    ImageView btnBack;
    View rootView;
    List<PRODUCT> productList = new ArrayList<PRODUCT>();
    List<INVOICE_DETAIL> details = new ArrayList<INVOICE_DETAIL>();

    INVOICE invoice;
    int invoice_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_invoice);

        recyclerView = findViewById(R.id.cartView);

        invoice_id = getIntent().getIntExtra("invoice_id", 1);


        rootView = findViewById(R.id.rootView);
        subTotal = findViewById(R.id.subTotalTxt);
        promotion = findViewById(R.id.promotionTxt);
        total = findViewById(R.id.totalTxt);
        address = findViewById(R.id.addressTxt);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi ImageView được nhấn
                onBackPressed();
            }
        });
        setLoad(true);
        new LoadInvoice().execute();

    }
    public void loadGiaoDien(List<PRODUCT> mProductList,List<INVOICE_DETAIL> mDetails){
        productList = mProductList;
        details = mDetails;
        Bundle extras = getIntent().getExtras(); // Lấy Bundle từ Intent
        if (extras != null) {
            String value = extras.getString("address"); // Lấy giá trị chuỗi từ Bundle
            address.setText(value);

        }

        NumberFormat money = NumberFormat.getCurrencyInstance();
        money.setMaximumFractionDigits(0);
        money.setCurrency(Currency.getInstance("VND"));

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        promotion.setText("0đ");

        subTotal.setText(formatter.format(invoice.getTotal_product()) + "đ");
        total.setText(formatter.format(invoice.getTotal_product()) + "đ");
        ThanhToanAdapter adapter = new ThanhToanAdapter(this,productList,details);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
    class LoadInvoice extends AsyncTask<Void, String, InvoiceAPI.GetInvoiceDetailResponse> {


        @Override
        protected InvoiceAPI.GetInvoiceDetailResponse doInBackground(Void... voids) {
            try {
                return new InvoiceAPI().getInvoiceById(invoice_id).body();

            }catch (IOException e){
                publishProgress("Lỗi kết nối!");
            }catch (Exception e){
                publishProgress(e.toString());
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(getBaseContext(), values[0], Toast.LENGTH_SHORT).show();
//            Log.e(TAG, "onProgressUpdate: ", e);
            setLoad(false);
            cancel(true);
        }


        @Override
        protected void onPostExecute(InvoiceAPI.GetInvoiceDetailResponse getCartResponse) {
            setLoad(false);
            productList = getCartResponse.getProductList();
            details = getCartResponse.getInvoiceDetail();
            invoice = getCartResponse.getInvoice();
            Log.d("Invoice detail", "onPostExecute: " + getCartResponse.getInvoiceDetail().size());
            Log.d("Invoice detail", "onPostExecute: " + invoice.getInvoice_id());
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

}