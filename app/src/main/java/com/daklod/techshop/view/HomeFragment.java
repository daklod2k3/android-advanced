package com.daklod.techshop.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.daklod.techshop.ResultActivity;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.daklod.techshop.MainActivity;
import com.daklod.techshop.SearchActivity;
import com.daklod.techshop.AllCategory;
import com.daklod.techshop.DTO.BANNER;
import com.daklod.techshop.DTO.CATEGORY;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.R;
import com.daklod.techshop.adapter.BannerViewAdapter;
import com.daklod.techshop.adapter.CategoryAdapter;
import com.daklod.techshop.adapter.ProductViewAdapter;
import com.daklod.techshop.controller.BannerApi;
import com.daklod.techshop.controller.CategoryAPI;
import com.daklod.techshop.controller.ProductAPI;
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
    TextView allProduct;
    ViewGroup mainView;

    //
    EditText search;
    //

    LottieAnimationView categoryLoadAnim, productLoadAnim, bannerLoadAnim;


    View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       if (fragmentView == null)
            fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryRecyclerView = getView().findViewById(R.id.categoryRecycler);
        allCategory = getView().findViewById(R.id.allCategoryImage);
        recentlyViewedRecycler = getView().findViewById(R.id.productRecycler);
        bannerSliderView = getView().findViewById(R.id.bannerSlider);
        bannerView = getView().findViewById(R.id.bannerImage);
        allProduct = getView().findViewById(R.id.show_all);

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

        allProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ResultActivity.class);
                intent.putExtra("search", "");
                startActivity(intent);
            }
        });

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
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext(), FlexDirection.ROW, FlexWrap.WRAP);

        layoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
//        layoutManager.set

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