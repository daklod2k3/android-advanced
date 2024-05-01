package com.daklod.techshop;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.controller.ProductAPI;
import com.daklod.techshop.R;
import com.daklod.techshop.view.CartActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class ProductDetails extends AppCompatActivity {


        ImageView img, back, cart;
        TextView proName, proPrice, proDesc, btnMinus, btnPlus, txtAmount;
        ImageView addCart;
        Button btnBuyNow;
        int amount = 1;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_details);

            Intent i = getIntent();
            int id = i.getIntExtra("id", -1);


            cart = findViewById(R.id.cart);
            proName = findViewById(R.id.productName);
            proDesc = findViewById(R.id.prodDesc);
            img = findViewById(R.id.big_image);
            proPrice = findViewById(R.id.productPrice);
            back = findViewById(R.id.back2);
            addCart = findViewById(R.id.addToCart);
            btnBuyNow = findViewById(R.id.btnBuyNow);
            btnMinus = findViewById(R.id.minusPro);
            btnPlus = findViewById(R.id.plusPro);
            txtAmount = findViewById(R.id.txtAmount);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ProductDetails.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });

            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(amount<=1) return;
                    txtAmount.setText(String.valueOf(--amount));
                }
            });
            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    txtAmount.setText(String.valueOf(++amount));
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
            addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Tạo đối tượng addCartBody với thông tin cần thiết
                    ProductAPI.addCartBody body = new ProductAPI.addCartBody(i.getIntExtra("id", -1), amount); // Thay productId và quantity bằng thông tin thích hợp

                    // Gọi phương thức addProductToCart trong ProductAPI
                    new AddProductToCartTask().execute(body);
                }
            });

            new LoadProductDetail().execute(id);
        }
    private class AddProductToCartTask extends AsyncTask<ProductAPI.addCartBody, Void, PRODUCT> {
        @Override
        protected PRODUCT doInBackground(ProductAPI.addCartBody... addCartBodies) {
            try {
                // Gọi phương thức addProductToCart trong ProductAPI và trả về kết quả
                return new ProductAPI().addProductToCart(addCartBodies[0]);
            } catch (IOException e) {
                Log.e(TAG, "Error adding product to cart", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(PRODUCT product) {
            if (product != null) {
                // Thêm sản phẩm vào giỏ hàng thành công
                Toast.makeText(ProductDetails.this, "Product added to cart successfully", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Product added to cart successfully");
            } else {
                // Xử lý khi thêm sản phẩm vào giỏ hàng thất bại
                Toast.makeText(ProductDetails.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to add product to cart");
            }
        }
    }

        private class LoadProductDetail extends AsyncTask<Integer, Void, List<PRODUCT>> {

            @Override
            protected List<PRODUCT> doInBackground(Integer... integers) {
                return new ProductAPI().getProductByID(integers[0]);
            }

            @Override
            protected void onPostExecute(List<PRODUCT> products) {
                if (products == null) {
                    return;
                }

                for (PRODUCT product:
                     products) {
                    proName.setText(product.getName());
                    proPrice.setText(String.valueOf(product.getPrice()) + "đ");
//                    proDesc.setText(product.getDescription());

                    // Load image using Picasso or any other image loading library
                    Picasso.get()
                            .load("http://daklod-backend.vercel.app/image/" + product.getImg_url())
                            .fit()
                            .error(R.drawable.no_img)
                            .into(img);
                    img.setImageBitmap(product.getBitmap());
                }
            }
        }


}
