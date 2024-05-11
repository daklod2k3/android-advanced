package com.daklod.techshop.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daklod.techshop.DTO.INVOICE_DETAIL;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.R;
import com.daklod.techshop.adapter.CartViewAdapter;
import com.daklod.techshop.adapter.ProductViewAdapter;
import com.daklod.techshop.controller.CartAPI;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.io.IOException;

import retrofit2.Response;

public class CartFragment extends Fragment {
    String TAG = "Cart Activity";
    RecyclerView recyclerViewCart;
    TextView txtTotal;
    CartViewAdapter cartViewAdapter;

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
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_cart, container, false);
//        return null;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewCart = getView().findViewById(R.id.RecycleViewCart);
        txtTotal = getView().findViewById(R.id.txtTotal);
        new LoadCartTask().execute();
    }

    class LoadCartTask extends AsyncTask<Void, String, CartAPI.GetCartResponse>{
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
            Toast.makeText(getView().getContext(), values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(CartAPI.GetCartResponse getCartResponse) {
             setCartViewAdapter(getCartResponse);
            for (INVOICE_DETAIL invoice : getCartResponse.getInvoiceDetail()) {
                for (PRODUCT product: getCartResponse.getProductList()) {
                    if(invoice.getProduct_id() == product.getProduct_id()) {
                        txtTotal.setText(invoice.getAmount() * product.getPrice()+"");
                    }
                }
            }
            Log.d(TAG, "onPostExecute: " + String.valueOf(getCartResponse.getInvoiceDetail().size()));
        }
    }

    void setCartViewAdapter(CartAPI.GetCartResponse cartResponse){
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setJustifyContent(JustifyContent.CENTER);

        recyclerViewCart.setLayoutManager(layoutManager);
        cartViewAdapter = new CartViewAdapter(getContext(), cartResponse.getProductList(), cartResponse.getInvoiceDetail());
        recyclerViewCart.setAdapter(cartViewAdapter);
//        recyclerViewCart.setAdapter(new CartViewAdapter(getView().getContext(), cartResponse.getProductList(), cartResponse.getInvoiceDetail()));
    }

}