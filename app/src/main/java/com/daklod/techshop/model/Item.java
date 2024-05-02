package com.rajendra.techshop.model;

public class Item {
    int idProduct;
    String nameProduct;
    int Amount;
    float price;
    String imgurl;

    public Item(String nameProduct, int idProduct,  float price, String imgurl) {
        this.nameProduct = nameProduct;
        this.idProduct = idProduct;
        this.price = price;
        this.imgurl = imgurl;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
