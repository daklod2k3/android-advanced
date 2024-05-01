package com.daklod.techshop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.daklod.techshop.DTO.CATEGORY;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.R;
import com.daklod.techshop.adapter.CartViewAdapter;
import com.daklod.techshop.controller.CategoryAPI;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerViewCart;
    CartViewAdapter cartViewAdapter;
    TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerViewCart = findViewById(R.id.RecycleViewCart);
        txtTotal = findViewById(R.id.txtTotal);
    }

}