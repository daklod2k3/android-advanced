package com.rajendra.techshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.rajendra.techshop.DTO.CATEGORY;
import com.rajendra.techshop.DTO.PRODUCT;
import com.rajendra.techshop.adapter.CategoryAdapter;
import com.rajendra.techshop.adapter.DiscountedProductAdapter;
import com.rajendra.techshop.adapter.ProductViewAdapter;
import com.rajendra.techshop.controller.CategoryAPI;
import com.rajendra.techshop.controller.ProductAPI;
import com.rajendra.techshop.model.DiscountedProducts;
import com.rajendra.techshop.view.HomeFragment;
import com.rajendra.techshop.view.SampleFragment;

import java.util.ArrayList;
import java.util.List;

import static com.rajendra.techshop.R.drawable.*;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReplaceFragment(new HomeFragment());

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d("fragment", String.valueOf(item.getItemId()));
            switch (item.getItemId()){
                case R.id.home:
                    ReplaceFragment(new HomeFragment());
                    break;
                case R.id.sample:
                    ReplaceFragment(new SampleFragment());
                    break;
            }
            return  true;
        });


    }

    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}
