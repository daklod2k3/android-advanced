package com.daklod.techshop.DTO;

import java.util.Date;

public class EMPLOYEE {
    private String name;
    private String address;
    private String phone;
    private String personal_id;
    private String sex;
    private Date birthday;
    private int user_id;
    private int employee_id;

    public EMPLOYEE() {
    }

    public EMPLOYEE(String name, String address, String phone, String personal_id, String sex, Date birthday, int user_id, int employee_id) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.personal_id = personal_id;
        this.sex = sex;
        this.birthday = birthday;
        this.user_id = user_id;
        this.employee_id = employee_id;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
