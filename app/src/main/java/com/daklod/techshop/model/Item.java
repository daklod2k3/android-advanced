package com.daklod.techshop.model;

public class Item {
    private int idProduct;
    private String nameProduct;
    private int amount;
    private float price;
    private String imgUrl;

    // Constructor
    public Item(int idProduct, String nameProduct, int amount, float price, String imgUrl) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.amount = amount;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    // Getters and Setters
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
        return amount;
    }

    public void setAmount(int amount) {
        if (amount >= 0) {
            this.amount = amount;
        } else {
            throw new IllegalArgumentException("Số lượng không được là số âm");
        }
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if (price >= 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("Giá không được là số âm");
        }
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
