package com.daklod.techshop.DTO;

import android.content.ClipData;

import com.daklod.techshop.model.Item;

import java.util.Date;
import java.util.List;

public class INVOICE {
    private int customer_id;
    private int employee_id;
    private int status_id;
    private String total;
    private int invoice_id;
    private Date date_created;
    private List<Item> item;

    public INVOICE() {
    }

    public INVOICE(int status_id, int invoice_id) {
        this.status_id = status_id;
        this.invoice_id = invoice_id;
    }

    public INVOICE(int customer_id, int employee_id, int status_id, String total, int invoice_id, Date date_created, List<Item> item) {
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.status_id = status_id;
        this.total = total;
        this.invoice_id = invoice_id;
        this.date_created = date_created;
        this.item = item;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
