package com.rajendra.techshop.DTO;

public class CUSTOMER {
    private String name;
    private String address;
    private String phone;
    private String personal_id;
    private int user_id;
    private int customer_id;

    public CUSTOMER() {
    }

    public CUSTOMER(String name, String address, String phone, String personal_id, int user_id, int customer_id) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.personal_id = personal_id;
        this.user_id = user_id;
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonal_id() {
        return personal_id;
    }

    public void setPersonal_id(String personal_id) {
        this.personal_id = personal_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
