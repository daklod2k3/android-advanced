package com.daklod.techshop.DTO;

import java.util.Date;

public class FUNCTION {
    private int function_id;
    private String name;
    private Date date_created;

    public FUNCTION() {
    }

    public FUNCTION(int function_id, String name, Date date_created) {
        this.function_id = function_id;
        this.name = name;
        this.date_created = date_created;
    }

    public int getFunction_id() {
        return function_id;
    }

    public void setFunction_id(int function_id) {
        this.function_id = function_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }
}
