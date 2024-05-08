package com.daklod.techshop.DTO;

import com.daklod.techshop.model.Item;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class INVOICE {
    private int customer_id;
    private int employee_id;
    private int status_id;
    private BigDecimal total; // Sử dụng BigDecimal cho tổng tiền để đảm bảo tính chính xác trong phép tính
                              // toán tiền tệ
    private int invoice_id;
    private Date date_created;
    private List<Item> items; // Đổi tên thuộc tính 'item' thành 'items' để phù hợp với tên

    public INVOICE() {
    }

    // Constructor cho việc tạo hóa đơn với status_id và invoice_id
    public INVOICE(int status_id, int invoice_id) {
        this.status_id = status_id;
        this.invoice_id = invoice_id;
    }

    public INVOICE(int customer_id, int employee_id, int status_id, BigDecimal total, int invoice_id, Date date_created,
            List<Item> items) {
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.status_id = status_id;
        this.total = total;
        this.invoice_id = invoice_id;
        this.date_created = date_created;
        this.items = items;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    // Phương thức tính tổng giá trị hóa đơn
    public BigDecimal calculateInvoiceTotal() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (items != null) {
            for (Item item : items) {
                BigDecimal itemPrice = BigDecimal.valueOf(item.getPrice());
                totalAmount = totalAmount.add(itemPrice.multiply(BigDecimal.valueOf(item.getAmount())));
            }
        }
        return totalAmount;
    }
}
