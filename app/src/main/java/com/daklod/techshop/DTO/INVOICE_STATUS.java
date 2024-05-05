package com.daklod.techshop.DTO;

public class INVOICE_STATUS {
    private int invoice_status_id;
    private  String name;


    public INVOICE_STATUS() {

    }
    public INVOICE_STATUS(int invoice_status_id, String name) {
        this.invoice_status_id = invoice_status_id;
        this.name = name;
    }

    public int getInvoice_status_id() {
        return invoice_status_id;
    }

    public void setInvoice_status_id(int invoice_status_id) {
        this.invoice_status_id = invoice_status_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
