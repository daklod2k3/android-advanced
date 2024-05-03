package com.rajendra.techshop.model;
import android.util.Log;
import java.util.ArrayList;

public class Recent {

    public static ArrayList<String> itemList = new ArrayList<>();

    public Recent(){};
    public static void loadData() {
        itemList.add("soi co don");
        itemList.add("Loli4");
        itemList.add("Loli3");
        itemList.add("Loli2");
        itemList.add("Loli1");
        Log.d("console", "loadData: ");
    }

    public static ArrayList<String> getData() {
        if(itemList.size() == 0) {
            loadData();
        }
        return itemList;
    }

    public static void addData(String name) {
        if (!itemList.contains(name)) {
            itemList.add(0,name);
        }
    }
}
