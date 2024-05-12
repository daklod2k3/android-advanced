package com.daklod.techshop.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.daklod.techshop.DTO.INVOICE_DETAIL;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.R;
import com.daklod.techshop.adapter.CartViewAdapter;
import com.daklod.techshop.adapter.ProductViewAdapter;
import com.daklod.techshop.controller.CartAPI;
import com.daklod.techshop.controller.LoginAPI;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Response;

public class CartFragment extends Fragment {
    String TAG = "Cart Activity";
    RecyclerView recyclerViewCart;
    TextView txtTotal;
    CartViewAdapter cartViewAdapter;

    List<PRODUCT> productList;
    List<INVOICE_DETAIL> details;

    View animationView;
    View rootView;
    TextView txtEmpty;
    View fragmentView;
    Button btn;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//
//        recyclerViewCart = findViewById(R.id.RecycleViewCart);
//        txtTotal = findViewById(R.id.txtTotal);
//        new LoadCartTask().execute();
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        inflater.inflate(R.layout.activity_main, container);
        if (fragmentView == null)
            fragmentView = inflater.inflate(R.layout.activity_cart, container, false);
        return fragmentView;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rootView = getView().findViewById(R.id.rootView);
        recyclerViewCart = getView().findViewById(R.id.RecycleViewCart);
        txtTotal = getView().findViewById(R.id.txtTotal);
        btn = getView().findViewById(R.id.btnSubmit);
        animationView = getView().findViewById(R.id.loadingAnimation);
        animationView.setActivated(true);
        txtEmpty = getView().findViewById(R.id.txtEmpty);
        new LoadCartTask().execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(), thanhtoanActivity.class);
                Bundle bundle = new Bundle();
                if(LoginAPI.user != null){
                    bundle.putString("address", LoginAPI.user.getAddress());// Đặt dữ liệu vào Bundle
                    Log.d(TAG, "address: " + LoginAPI.user.getAddress());
                }
//                bundle.putParcelableArrayList("productList", (ArrayList<? extends Parcelable>) productList);
//                bundle.putParcelableArrayList("details", (ArrayList<? extends Parcelable>) details);

                i.putExtras(bundle); // Đặt Bundle vào Intent
                startActivity(i);
//                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateTotal(){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        int total = 0;
        if (details != null)
            for (int i = 0; i < details.size(); i++){
                total += details.get(i).getAmount() * productList.get(i).getPrice();
            }
        txtTotal.setText(formatter.format(total)+"đ");
    }

    private void setLoad(boolean load){
        if (!load){
            animationView.setVisibility(View.GONE);
            rootView.setVisibility(View.VISIBLE);
            return;
        }

        animationView.setVisibility(View.VISIBLE);
        rootView.setVisibility(View.GONE);
    }

    class LoadCartTask extends AsyncTask<Void, String, CartAPI.GetCartResponse>{

        public LoadCartTask(){

        }

        @Override
        protected CartAPI.GetCartResponse doInBackground(Void... voids) {
            try {
                Response<CartAPI.GetCartResponse> response = new CartAPI().getCart();
                if (response.isSuccessful()){
                    return response.body();
                }
                publishProgress(response.message());

            }catch (IOException e){
                publishProgress("Lỗi kết nối!");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            setLoad(false);
            Toast.makeText(getView().getContext(), values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(CartAPI.GetCartResponse getCartResponse) {
            setLoad(false);
            if (getCartResponse.getInvoiceDetail().size() > 0){
                txtEmpty.setVisibility(View.GONE);

            }else {
                txtEmpty.setVisibility(View.VISIBLE);
            }
            productList = getCartResponse.getProductList();
            details = getCartResponse.getInvoiceDetail();
            setCartViewAdapter(getCartResponse);
            updateTotal();
            Log.d(TAG, "onPostExecute: " + String.valueOf(getCartResponse.getInvoiceDetail().size()));
        }
    }

    void setCartViewAdapter(CartAPI.GetCartResponse cartResponse){
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setJustifyContent(JustifyContent.CENTER);

        recyclerViewCart.setLayoutManager(layoutManager);
        cartViewAdapter = new CartViewAdapter(this, cartResponse.getProductList(), cartResponse.getInvoiceDetail());
        recyclerViewCart.setAdapter(cartViewAdapter);
//        recyclerViewCart.setAdapter(new CartViewAdapter(getView().getContext(), cartResponse.getProductList(), cartResponse.getInvoiceDetail()));
    }

    public static class ChangeAmountTask extends AsyncTask<CartAPI.AddCartBody, String, Response<Void>>{

        CartFragment view;

        public ChangeAmountTask(CartFragment view){
            this.view = view;
            view.setLoad(true);
        }
        @Override
        protected Response<Void> doInBackground(CartAPI.AddCartBody... addCartBodies) {
            try {
                return new CartAPI().addCart(addCartBodies[0]);


            }catch (IOException e){
                publishProgress("Lỗi server");
                cancel(true);
            }catch (Exception e){
                publishProgress(e.toString());
                cancel(true);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(view.getContext(), "Lỗi server", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Response<Void> voidResponse) {
            view.reloadCart();
        }
    }

    public void reloadCart(){
        new LoadCartTask().execute();
    }

}