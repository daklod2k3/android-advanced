package com.daklod.techshop.DTO;

public class INVOICE_STATUS {
    private int invoiceStatusId;
    private String name;

    public INVOICE_STATUS(int invoiceStatusId, String name) {
        this.invoiceStatusId = invoiceStatusId;
        this.name = name;
    }

    public int getInvoiceStatusId() {
        return invoiceStatusId;
    }

    public void setInvoiceStatusId(int invoiceStatusId) {
        this.invoiceStatusId = invoiceStatusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
