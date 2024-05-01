
package com.daklod.techshop.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daklod.techshop.DTO.PRODUCT;
import com.rajendra.techshop.R;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.ProductDetailsViewHolder> {
    Context context;
    PRODUCT product;
    @NonNull
    @Override
    public ProductDetailsAdapter.ProductDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_product_details, parent, false);
        return new ProductDetailsAdapter.ProductDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailsAdapter.ProductDetailsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProductDetailsViewHolder extends  RecyclerView.ViewHolder {
        public ProductDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
