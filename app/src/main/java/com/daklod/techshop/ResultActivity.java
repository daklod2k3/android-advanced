package com.daklod.techshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.daklod.techshop.DTO.CATEGORY;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.R;
import com.daklod.techshop.adapter.CategoryAdapter;
import com.daklod.techshop.adapter.ProductViewAdapter;
import com.daklod.techshop.adapter.ResultViewAdapter;
import com.daklod.techshop.controller.CategoryAPI;
import com.daklod.techshop.controller.ProductAPI;
import com.daklod.techshop.view.HomeFragment;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.UArraySortingKt;

public class ResultActivity extends AppCompatActivity {
    private ImageView back;
    private ConstraintLayout main,layoutLoc;
    private static ConstraintLayout nav;
    private EditText inputResult;
    private static EditText startPrice, endPrice;
    private Button btnPrice, btnLoc, prev, next;
    private static RecyclerView listResult,category;
    private static ProductViewAdapter listAdapter;
    private ResultViewAdapter listCategory;
    private LottieAnimationView load,categoryLoad;
    private static TextView textNav;
    private static List<PRODUCT> listData, listDataCurrent;
    private static ArrayList<Integer> listChecked = new ArrayList<>();
    private String dataIntent = "";
    private int statePrice = 0;
    private int currentNav = 1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        main = findViewById(R.id.main3);
        back = findViewById(R.id.back4);
        inputResult = findViewById(R.id.editTextSearch4);
        inputResult.setFocusableInTouchMode(false);
        btnLoc = findViewById(R.id.filter);
        btnPrice = findViewById(R.id.sortPrice);
        listResult = findViewById(R.id.productSearch);
        category = findViewById(R.id.categorySearch);
        categoryLoad = findViewById(R.id.loadCategory);
        load = findViewById(R.id.loadSearch);
        layoutLoc = findViewById(R.id.viewLoc);
        nav = findViewById(R.id.nav);
        textNav = findViewById(R.id.textNav);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        startPrice = findViewById(R.id.startPrice);
        endPrice = findViewById(R.id.endPrice);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("search")) {
            dataIntent = intent.getStringExtra("search");
            if (dataIntent != null) {
                inputResult.setText(dataIntent);
            }
        }

        startPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().matches("\\d*")) {
                    startPrice.setText(s.toString().replaceAll("[^\\d]", ""));
                    startPrice.setSelection(startPrice.getText().length());
                }
                handleRangePrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        endPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().matches("\\d*")) {
                    endPrice.setText(s.toString().replaceAll("[^\\d]", ""));
                    endPrice.setSelection(endPrice.getText().length());
                }
                handleRangePrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortPrice();
                handleSortPrice();
            }
        });

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutLoc.getVisibility() == View.GONE) {
                    layoutLoc.setVisibility(View.VISIBLE);
                } else {
                    layoutLoc.setVisibility(View.GONE);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNav(1);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNav(2);
            }
        });

        inputResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this, SearchActivity.class);
                i.putExtra("search", inputResult.getText().toString());
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new LoadProduct().execute();
        new LoadCategory().execute();
    }

    public void sortPrice() {
        if(statePrice == 1 || statePrice == 0) {
            Drawable doubleArrowDrawable = ContextCompat.getDrawable(this, R.drawable.sort_up);
            btnPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, doubleArrowDrawable, null);
            statePrice = 2;
        } else {
            Drawable doubleArrowDrawable = ContextCompat.getDrawable(this, R.drawable.sort_down);
            btnPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, doubleArrowDrawable, null);
            statePrice = 1;
        }
    }
    public void handleSortPrice() {
        if(statePrice == 1) {
            int n = listData.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (listData.get(j).getPrice() < listData.get(j + 1).getPrice()) {
                        PRODUCT temp = listData.get(j);
                        listData.set(j, listData.get(j+1));
                        listData.set(j + 1, temp);
                    }
                }
            }
            if(listChecked.size() != 0) {
                checkedCategory();
            } else {
                handleRangePrice();
            }
        } else if(statePrice == 2) {
            int n = listData.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (listData.get(j).getPrice() > listData.get(j + 1).getPrice()) {
                        PRODUCT temp = listData.get(j);
                        listData.set(j, listData.get(j+1));
                        listData.set(j + 1, temp);
                    }
                }
            }

            if(listChecked.size() != 0) {
                checkedCategory();
            } else {
                handleRangePrice();
            }
        }
    }
    class LoadProduct extends AsyncTask<Void, Void, List<PRODUCT>> {
        @Override
        protected List<PRODUCT> doInBackground(Void... voids) {
            List<PRODUCT> productList = new ProductAPI().getProductBySearch(dataIntent);
            for (PRODUCT product: productList){
//                product.loadBitmap();
            }
            return productList;
        }

        @Override
        protected void onPostExecute(List<PRODUCT> productList) {
//            Log.d("request", categoryList.get(0).getImgUrl().toString());
            main.removeView(load);
            if(productList.size() > 0) {
                nav.setVisibility(View.VISIBLE);
            }
            setNewProductRecycler(productList,1,true);
        }
    }
    public static void updateNav(int idx,List<PRODUCT> list) {
        textNav.setText(idx + "/" + (int)Math.ceil(list.size()/4 + 0.0000001));
    }
    public void handleNav(int idx) {
        int value = Character.getNumericValue(textNav.getText().charAt(0));
        int max = Character.getNumericValue(textNav.getText().charAt(textNav.getText().length() - 1));
        if(idx == 1) {
            if(value + 1 <= max) {
                setNewProductRecycler(listDataCurrent,value + 1,false);
            }
        } else if(idx == 2) {
            if(value - 1 >= 1) {
                setNewProductRecycler(listDataCurrent,value - 1,false);
            }
        }
    }

    public void handleRangePrice() {
        listDataCurrent = new ArrayList<>();

        for (int i = 0; i < listData.size(); i++) {
            if(startPrice.getText().toString().equalsIgnoreCase("")
                    && endPrice.getText().toString().equalsIgnoreCase(""))
            {
                listDataCurrent.add(listData.get(i));
            }
            else if(!startPrice.getText().toString().equalsIgnoreCase("")
                    && !endPrice.getText().toString().equalsIgnoreCase(""))
            {
                if(listData.get(i).getPrice() >= Integer.parseInt(startPrice.getText().toString())
                        && listData.get(i).getPrice() <= Integer.parseInt(endPrice.getText().toString()))
                {
                    listDataCurrent.add(listData.get(i));
                }
            } else if(!startPrice.getText().toString().equalsIgnoreCase("")) {
                if(listData.get(i).getPrice() >= Integer.parseInt(startPrice.getText().toString())) {
                    listDataCurrent.add(listData.get(i));
                }
            } else {
                if(listData.get(i).getPrice() <= Integer.parseInt(endPrice.getText().toString())) {
                    listDataCurrent.add(listData.get(i));
                }
            }
        }

        if(listDataCurrent.size() > 4) {
            List<PRODUCT> newArray = listDataCurrent.subList(0, Math.min(listDataCurrent.size(), 4));
            updateNav(1,listDataCurrent);
            listAdapter.setList(newArray);
        } else {
            updateNav(1,listDataCurrent);
            listAdapter.setList(listDataCurrent);
        }
        listResult.setAdapter(listAdapter);
    }
    class LoadCategory extends AsyncTask<Void, Void, List<CATEGORY>> {
        @Override
        protected List<CATEGORY> doInBackground(Void... voids) {
            return new CategoryAPI().getCategory();
        }

        @Override
        protected void onPostExecute(List<CATEGORY> categoryList) {
//            Log.d("request", categoryList.get(0).getImgUrl().toString());
            if (categoryList == null) return;
            CATEGORY.mapName(categoryList);
            main.removeView(categoryLoad);
            setCategoryRecycler(categoryList);
        }
    }
    private void setNewProductRecycler(List<PRODUCT> recentlyViewedDataList,int idx,boolean change) {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        listResult.setLayoutManager(layoutManager);
        List<PRODUCT> newArray;

        if(change == true) {
//            Log.d("console", "change");
            listDataCurrent = recentlyViewedDataList;
            listData = recentlyViewedDataList;
        }

        if(idx == 1) {
            newArray = listDataCurrent.subList(0, Math.min(listDataCurrent.size(), 4));
        } else {
            newArray = listDataCurrent.subList(0 + (idx-1)*4, Math.min(listDataCurrent.size(), 4*idx));
        }
        updateNav(idx,listDataCurrent);
        listAdapter = new ProductViewAdapter(this, newArray);
        listResult.setAdapter(listAdapter);
    }
    private void setCategoryRecycler(List<CATEGORY> categoryDataList) {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
        category.setLayoutManager(layoutManager);
        listCategory = new ResultViewAdapter(this, categoryDataList);
        category.setAdapter(listCategory);
    }
    public static void checkedCategory() {
        listDataCurrent = new ArrayList<>();

        for (int i = 0; i < listData.size(); i++) {
            for(Integer elem: listChecked) {
                if(listData.get(i).getCategory_id() == elem){
                    if(startPrice.getText().toString().equalsIgnoreCase("")
                            && endPrice.getText().toString().equalsIgnoreCase(""))
                    {
                        listDataCurrent.add(listData.get(i));
                        break;
                    }
                    else if(!startPrice.getText().toString().equalsIgnoreCase("")
                            && !endPrice.getText().toString().equalsIgnoreCase(""))
                    {
                        if(listData.get(i).getPrice() >= Integer.parseInt(startPrice.getText().toString())
                                && listData.get(i).getPrice() <= Integer.parseInt(endPrice.getText().toString()))
                        {
                            listDataCurrent.add(listData.get(i));
                            break;
                        }
                    } else if(!startPrice.getText().toString().equalsIgnoreCase("")) {
                        if(listData.get(i).getPrice() >= Integer.parseInt(startPrice.getText().toString())) {
                            listDataCurrent.add(listData.get(i));
                            break;
                        }
                    } else {
                        if(listData.get(i).getPrice() <= Integer.parseInt(endPrice.getText().toString())) {
                            listDataCurrent.add(listData.get(i));
                            break;
                        }
                    }
                }
            }
        }

//        Log.d("console", "listData: " + listData.size());

        if(listDataCurrent.size() == 0) {
            nav.setVisibility(View.GONE);
        } else {
            nav.setVisibility(View.VISIBLE);
        }

        if(listDataCurrent.size() > 4) {
            List<PRODUCT> newArray = listDataCurrent.subList(0, Math.min(listDataCurrent.size(), 4));
            updateNav(1,listDataCurrent);
            listAdapter.setList(newArray);
        } else {
            updateNav(1,listDataCurrent);
            listAdapter.setList(listDataCurrent);
        }
        listResult.setAdapter(listAdapter);
    }
    public static ArrayList<Integer> getListChecked() {
        return listChecked;
    }
}
