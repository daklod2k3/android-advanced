package com.daklod.techshop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.R;
import com.daklod.techshop.adapter.ThanhToanAdapter;
import com.daklod.techshop.controller.InvoiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class thanhtoanActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView subTotal,totalTax,total,address;
    Button btnThanhToan;
    List<PRODUCT> productList = new ArrayList<PRODUCT>();
    int tongTienChuaThue = 0;
    int thue = 200;
    int tongTien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);

        recyclerView = findViewById(R.id.cartView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        PRODUCT p1 = new PRODUCT(1, "Laptop1", 100, "https://vcdn-sohoa.vnecdn.net/2021/01/21/HP-Elite-Folio-Front-Left-Forw-6107-5267-1611217952.jpg", 1, 1, "abc");
        productList.add(p1);
        //fake data
//        productList.add(new PRODUCT(1, "Laptop1", 100, "https://vcdn-sohoa.vnecdn.net/2021/01/21/HP-Elite-Folio-Front-Left-Forw-6107-5267-1611217952.jpg", 10, 1, "abc"));
        productList.add(new PRODUCT(2, "Laptop1", 100, "https://vcdn-sohoa.vnecdn.net/2021/01/21/HP-Elite-Folio-Front-Left-Forw-6107-5267-1611217952.jpg", 2, 1, "abc"));
        productList.add(new PRODUCT(3, "Laptop1", 100, "https://vcdn-sohoa.vnecdn.net/2021/01/21/HP-Elite-Folio-Front-Left-Forw-6107-5267-1611217952.jpg", 3, 1, "abc"));
        productList.add(new PRODUCT(4, "Laptop1", 100, "https://vcdn-sohoa.vnecdn.net/2021/01/21/HP-Elite-Folio-Front-Left-Forw-6107-5267-1611217952.jpg", 4, 1, "abc"));
        productList.add(new PRODUCT(5, "Laptop1", 100, "https://vcdn-sohoa.vnecdn.net/2021/01/21/HP-Elite-Folio-Front-Left-Forw-6107-5267-1611217952.jpg", 5, 1, "abc"));
        subTotal = findViewById(R.id.subTotalTxt);
        totalTax = findViewById(R.id.totalTaxTxt);
        total = findViewById(R.id.totalTxt);
        address = findViewById(R.id.addressTxt);
        btnThanhToan = findViewById(R.id.btnOrderNow);
        Bundle extras = getIntent().getExtras(); // Lấy Bundle từ Intent
        if (extras != null) {
            String value = extras.getString("address"); // Lấy giá trị chuỗi từ Bundle
            address.setText(value);
        }
        for (int i = 0; i < productList.size();i++){
            tongTienChuaThue += productList.get(i).getPrice()*productList.get(i).getAmount();
        }
        tongTien = tongTienChuaThue - thue;
        subTotal.setText(tongTienChuaThue+"");
        totalTax.setText(thue+"");
        total.setText(tongTien+"");
        ThanhToanAdapter adapter = new ThanhToanAdapter(this,productList);
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
}