package com.rajendra.techshop.adapter;
import java.util.ArrayList;
import com.rajendra.techshop.R;

import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ResultViewAdapter /*extends ArrayAdapter<String>*/ {

    private ArrayList<String> itemList;
    private Context context;

//    public ResultViewAdapter(Context context, ArrayList<String> itemList) {
//        super(context, 0, itemList);
//        this.itemList = itemList;
//        this.context = context;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        String currentItem = itemList.get(position);
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
//        }
//
//        TextView textView = convertView.findViewById(R.id.textView3);
//        textView.setText(currentItem);
//
//        return convertView;
//    }
}