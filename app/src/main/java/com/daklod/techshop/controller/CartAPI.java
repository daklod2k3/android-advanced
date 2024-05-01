package com.daklod.techshop.controller;

public class CartAPI extends Api{
    public static class CartBody {
        int product_id;
        int amount;

        public CartBody(int product_id, int amount) {
            this.product_id = product_id;
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }
    }

}
