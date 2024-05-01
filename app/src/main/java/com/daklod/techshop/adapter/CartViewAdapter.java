package com.daklod.techshop.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.R;

import java.util.List;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.CartViewHolder> {
    Context context;
    List<PRODUCT> CartList;

    public CartViewAdapter(Context context, List<PRODUCT> cartList) {
        this.context = context;
        CartList = cartList;
    }

    @NonNull
    @Override
    public CartViewAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewAdapter.CartViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView img, delete;
        TextView title, price, totalPrice, minus, plus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgOrdered);
            delete = itemView.findViewById(R.id.imgDelete);
            title = itemView.findViewById(R.id.txtTitle);
            price = itemView.findViewById(R.id.EachItemPrice);
            totalPrice = itemView.findViewById(R.id.txtPriceTotal);
            minus = itemView.findViewById(R.id.minusItem);
            plus = itemView.findViewById(R.id.plusItem);
        }

    }
}
