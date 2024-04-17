package com.rajendra.techshop.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.rajendra.techshop.AllCategory;
import com.rajendra.techshop.DTO.CATEGORY;
import com.rajendra.techshop.DTO.PRODUCT;
import com.rajendra.techshop.MainActivity;
import com.rajendra.techshop.R;
import com.rajendra.techshop.adapter.CategoryAdapter;
import com.rajendra.techshop.adapter.DiscountedProductAdapter;
import com.rajendra.techshop.adapter.ProductViewAdapter;
import com.rajendra.techshop.controller.CategoryAPI;
import com.rajendra.techshop.controller.ProductAPI;

import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;
    DiscountedProductAdapter discountedProductAdapter;
//    List<DiscountedProducts> discountedProductsList;

    CategoryAdapter categoryAdapter;
    List<CATEGORY> categoryList;

    ProductViewAdapter recentlyViewedAdapter;
//    List<RecentlyViewed> recentlyViewedList;

    TextView allCategory;
    ViewGroup mainView;

    LottieAnimationView categoryLoadAnim, productLoadAnim, saleLoadAnim;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflater.inflate(R.layout.activity_main, container);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        discountRecyclerView = getView().findViewById(R.id.discountedRecycler);
        categoryRecyclerView = getView().findViewById(R.id.categoryRecycler);
        allCategory = getView().findViewById(R.id.allCategoryImage);
        recentlyViewedRecycler = getView().findViewById(R.id.productRecycler);

        categoryLoadAnim = getView().findViewById(R.id.categoryLoadAnimation);
        productLoadAnim = getView().findViewById(R.id.productLoadAnimation);
        saleLoadAnim = getView().findViewById(R.id.saleLoadAnimation);

        mainView = (ViewGroup) view;
        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AllCategory.class);
                startActivity(i);
            }
        });
        new LoadCategory().execute();
        new LoadProduct().execute();
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
            mainView.removeView(categoryLoadAnim);
            setCategoryRecycler(categoryList);

        }
    }

    class LoadProduct extends AsyncTask<Void, Void, List<PRODUCT>>{
        @Override
        protected List<PRODUCT> doInBackground(Void... voids) {
            List<PRODUCT> productList = new ProductAPI().getProduct();
            for (PRODUCT product: productList){
//                product.loadBitmap();
            }
            return productList;
        }

        @Override
        protected void onPostExecute(List<PRODUCT> productList) {
//            Log.d("request", categoryList.get(0).getImgUrl().toString());
            mainView.removeView(productLoadAnim);
            setNewProductRecycler(productList);
        }
    }

    private void setCategoryRecycler(List<CATEGORY> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(getContext(), categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setNewProductRecycler(List<PRODUCT> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recentlyViewedRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new ProductViewAdapter(getContext(), recentlyViewedDataList);
        recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
    }


}