package com.rajendra.techshop.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.rajendra.techshop.AllCategory;
import com.rajendra.techshop.SearchActivity;
import com.rajendra.techshop.DTO.BANNER;
import com.rajendra.techshop.DTO.CATEGORY;
import com.rajendra.techshop.DTO.PRODUCT;
import com.rajendra.techshop.MainActivity;
import com.rajendra.techshop.R;
import com.rajendra.techshop.adapter.BannerViewAdapter;
import com.rajendra.techshop.adapter.CategoryAdapter;
import com.rajendra.techshop.adapter.DiscountedProductAdapter;
import com.rajendra.techshop.adapter.ProductViewAdapter;
import com.rajendra.techshop.controller.BannerApi;
import com.rajendra.techshop.controller.CategoryAPI;
import com.rajendra.techshop.controller.ProductAPI;

import com.smarteist.autoimageslider.SliderView;
// test
import android.widget.Toast;
import android.view.MotionEvent;
import android.widget.EditText;

import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;

    SliderView bannerSliderView;
    CardView bannerView;

    CategoryAdapter categoryAdapter;

    ProductViewAdapter recentlyViewedAdapter;
//    List<RecentlyViewed> recentlyViewedList;

    TextView allCategory;
    ViewGroup mainView;

    //
    EditText search;
    //

    LottieAnimationView categoryLoadAnim, productLoadAnim, bannerLoadAnim;


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

        categoryRecyclerView = getView().findViewById(R.id.categoryRecycler);
        allCategory = getView().findViewById(R.id.allCategoryImage);
        recentlyViewedRecycler = getView().findViewById(R.id.productRecycler);
        bannerSliderView = getView().findViewById(R.id.bannerSlider);
        bannerView = getView().findViewById(R.id.bannerImage);

        categoryLoadAnim = getView().findViewById(R.id.categoryLoadAnimation);
        productLoadAnim = getView().findViewById(R.id.productLoadAnimation);
        bannerLoadAnim = getView().findViewById(R.id.bannerLoadAnim);
        // test
        search = getView().findViewById(R.id.editTextSearch);
        search.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
            }
            return false;
        });
        // test


        mainView = (ViewGroup) view.findViewById(R.id.mainView);
        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AllCategory.class);
                startActivity(i);
            }
        });
        new LoadCategory().execute();
        new LoadProduct().execute();
        new LoadBanner().execute();
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

    class LoadBanner extends AsyncTask<Void, Void, List<BANNER>> {
        @Override
        protected List<BANNER> doInBackground(Void... voids) {
            return new BannerApi().getBanner();
        }

        @Override
        protected void onPostExecute(List<BANNER> bannerList) {
//            Log.d("request", categoryList.get(0).getImgUrl().toString());
            if (bannerList == null) return;
            mainView.removeView(bannerLoadAnim);
            setBannerView(bannerList);

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
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext(), FlexDirection.ROW, FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
//        layoutManager.d
//        layoutManager.set
//        layoutManager.scroll;
//        layoutManager.set
        recentlyViewedRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new ProductViewAdapter(getContext(), recentlyViewedDataList);
        recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
    }

    private void setBannerView(List<BANNER> bannerList){
        bannerSliderView.setSliderAdapter(new BannerViewAdapter(bannerList));
        bannerSliderView.startAutoCycle();
        bannerView.setVisibility(View.VISIBLE);
    }

}