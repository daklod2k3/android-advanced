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

import com.daklod.techshop.R;
import com.daklod.techshop.controller.CartAPI;

import java.io.IOException;

import retrofit2.Response;

public class CartFragment extends Fragment {
    String TAG = "Cart Activity";
    RecyclerView recyclerViewCart;
    TextView txtTotal;

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
            Log.d(TAG, "onPostExecute: " + String.valueOf(getCartResponse.getInvoiceDetail().size()));
        }
    }

    void setCartViewAdapter(CartAPI.GetCartResponse cartResponse){
//        recyclerViewCart.setAdapter(new CartViewAdapter());
    }

}