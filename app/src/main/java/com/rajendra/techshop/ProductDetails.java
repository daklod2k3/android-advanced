package com.rajendra.techshop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajendra.techshop.DTO.CATEGORY;
import com.rajendra.techshop.DTO.PRODUCT;
import com.rajendra.techshop.controller.CategoryAPI;
import com.rajendra.techshop.controller.ProductAPI;
import com.rajendra.techshop.view.CartActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetails extends AppCompatActivity {


        ImageView img, back, cart;
        TextView proName, proPrice, proDesc;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_details);

            Intent i = getIntent();
            String id = i.getStringExtra("id");

            cart = findViewById(R.id.cart);
            proName = findViewById(R.id.productName);
            proDesc = findViewById(R.id.prodDesc);
            img = findViewById(R.id.big_image);
            proPrice = findViewById(R.id.productPrice);
            back = findViewById(R.id.back2);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ProductDetails.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });

            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ProductDetails.this, CartActivity.class);
                    startActivity(i);
                    finish();
                }
            });

            new LoadProductDetail().execute(id);
        }

        private class LoadProductDetail extends AsyncTask<String, Void, PRODUCT> {

            @Override
            protected PRODUCT doInBackground(String... strings) {
                return new ProductAPI().getProductByID(strings[0]);
            }

            @Override
            protected void onPostExecute(PRODUCT product) {
                if (product != null) {
                    proName.setText(product.getName());
                    proPrice.setText(String.valueOf(product.getPrice()));
//                    proDesc.setText(product.getDescription());

                    // Load image using Picasso or any other image loading library
                    Picasso.get()
                            .load("http://daklod-backend.vercel.app/image/" + product.getImg_url())
                            .fit()
                            .error(R.drawable.no_img)
                            .into(img);
                }
            }
        }


}
