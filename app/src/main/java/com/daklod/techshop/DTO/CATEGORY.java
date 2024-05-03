package com.daklod.techshop.DTO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.daklod.techshop.R;

import java.util.List;

public class CATEGORY {
    int id;
    String name;
    Integer imgUrl;

    public Integer getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Integer imgUrl) {
        this.imgUrl = imgUrl;
    }


    public CATEGORY(int id, Integer imgUrl, String name){
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public CATEGORY(int id, String name) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgMapping(name);
    }

    public Integer imgMapping(String name){
        String[] nameMap = new String[]{"Laptop", "Iphone", "Android", "Phụ kiện"};
        Integer[] imgMap  = new Integer[]{
                R.drawable.ic_laptop_windows,
                R.drawable.ic_smartphone,
                R.drawable.ic_smartphone,
                R.drawable.ic_devices_other
        };
        Log.d("for", String.valueOf(nameMap.length));
        for (int i = 0; i < nameMap.length; i++){
            Log.d("for", nameMap.toString());
            if (nameMap[i].equals(name))
                return imgMap[i];
        }
        return R.drawable.ic_devices_other;
    }

    public static void mapName(List<CATEGORY> categoryList) {
        for (CATEGORY item : categoryList){
            item.imgUrl = item.imgMapping(item.name);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return id + name + imgUrl;
    }
}
