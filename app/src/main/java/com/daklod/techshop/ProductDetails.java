package com.daklod.techshop;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.daklod.techshop.controller.CartAPI;
import com.daklod.techshop.controller.ProductAPI;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DecimalFormat;

import retrofit2.Response;

public class ProductDetails extends AppCompatActivity {


        ImageView img, back;
        ConstraintLayout layout;
        TextView proName, proPrice, proDesc, btnMinus, btnPlus, txtAmount;
        Button addCart;
        Button btnBuyNow;
        int amount = 1;
        int amountMax;

        PRODUCT product;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_details);

            Intent i = getIntent();
            int id = Integer.parseInt(i.getStringExtra("id"));


            layout = findViewById(R.id.detailLayout);
            layout.setVisibility(View.GONE);
            proName = findViewById(R.id.productName);
            proDesc = findViewById(R.id.txtMoTaChiTiet);
            img = findViewById(R.id.productImg);
            proPrice = findViewById(R.id.productPrice);
            back = findViewById(R.id.imgBack1);
            addCart = findViewById(R.id.addToCart);
            btnBuyNow = findViewById(R.id.btnBuyNow);
            btnMinus = findViewById(R.id.minusPro);
            btnPlus = findViewById(R.id.plusPro);
            txtAmount = findViewById(R.id.txtAmount);

            txtAmount.setText("1");

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
                    if(amount >= amountMax) {
                        Toast.makeText(ProductDetails.this,"Product quantity is not enough", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    txtAmount.setText(String.valueOf(++amount));
                }
            });


            addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AddCartTask().execute(new CartAPI.AddCartBody(product.getProduct_id(), Integer.valueOf(txtAmount.getText().toString())));
                }
            });

            new LoadProductDetail().execute(id);
        }



        private class AddCartTask extends AsyncTask<CartAPI.AddCartBody, String, Void>{
            @Override
            protected Void doInBackground(CartAPI.AddCartBody... addCartBodies) {
                try {
                    Response<Void> response = new CartAPI().addCart(addCartBodies[0]);
                    if (response.isSuccessful()){
//                        Toast.makeText(getBaseContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        return null;
                    }else {
                        publishProgress(response.message());
                    }
                }catch (IOException e){
                    Log.e(TAG, "doInBackground: ", e);
                    publishProgress("Lỗi kết nối! Thêm cart không thành công");
                }catch (Exception e){
                    Log.e(TAG, "doInBackground: ", e);
                    publishProgress(e.toString());
                }

                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                Toast.makeText(getBaseContext(), values[0], Toast.LENGTH_SHORT).show();
                cancel(true);
            }

            @Override
            protected void onPostExecute(Void unused) {
                Toast.makeText(getBaseContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            }
        }


        private class LoadProductDetail extends AsyncTask<Integer, Void, PRODUCT> {

            @Override
            protected PRODUCT doInBackground(Integer... integers) {
                return new ProductAPI().getProductByID(integers[0]).get(0);
            }

            @Override
            protected void onPostExecute(PRODUCT product) {
                if (product == null) {
                    return;
                }

                setProduct(product);

                DecimalFormat formatter = new DecimalFormat("#,###,###");
                proName.setText(product.getName());
                proPrice.setText(formatter.format(product.getPrice()) +"đ");
                amountMax = product.getAmount();
                proDesc.setText(product.getDescription());

                // Load image using Picasso or any other image loading library
                Picasso.get()
                        .load("http://daklod-backend.vercel.app/image/" + product.getImg_url())
                        .fit()
                        .error(R.drawable.no_img)
                        .into(img);
                img.setImageBitmap(product.getBitmap());
                layout.setVisibility(View.VISIBLE);
            }
        }

    public void setProduct(PRODUCT product) {
        this.product = product;
    }
}
