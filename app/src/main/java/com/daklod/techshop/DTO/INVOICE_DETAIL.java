package com.daklod.techshop.DTO;

import java.math.BigDecimal;

public class INVOICE_DETAIL {
    private int user_id;
    private int invoice_id;
    private int amount;
    private BigDecimal total;
    private BigDecimal tax;
    private int product_id;

    public INVOICE_DETAIL() {
    }

    public INVOICE_DETAIL(int user_id, int invoice_id, int amount, BigDecimal total, BigDecimal tax, int product_id) {
        this.user_id = user_id;
        this.invoice_id = invoice_id;
        this.amount = amount;
        this.total = total;
        this.tax = tax;
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
