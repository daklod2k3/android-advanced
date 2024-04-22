package com.rajendra.techshop.DTO;

import android.graphics.Bitmap;
import android.util.Log;

import com.squareup.picasso.Picasso;

public class PRODUCT {
    int product_id;
    String name;
    int price;
    String img_url;
    int amount;
    int category_id;
    Bitmap bitmap;

    public PRODUCT(int product_id, String name, int price, String img_url, int amount, int category_id) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.img_url = img_url;
        this.amount = amount;
        this.category_id = category_id;
    }

    public void loadBitmap(){
        try {
            Log.d("img", "http://121.0.0.1:3000/image/" + this.img_url);
            bitmap = Picasso.get()
                .load("http://127.0.0.1:3000/image/" + this.img_url)
                    .get();
        }catch (Exception e){
            Log.e("img", e.toString());
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
