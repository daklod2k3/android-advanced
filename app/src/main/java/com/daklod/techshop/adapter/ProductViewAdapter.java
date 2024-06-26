package com.daklod.techshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.ProductDetails;
import com.daklod.techshop.R;
import com.daklod.techshop.controller.Api;
import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.RecentlyViewedViewHolder> {

    Context context;
    List<PRODUCT> recentlyViewedList;

    public ProductViewAdapter(Context context, List<PRODUCT> recentlyViewedList) {
        this.context = context;
        this.recentlyViewedList = recentlyViewedList;
    }

    @NonNull
    @Override
    public RecentlyViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_view, parent, false);

        return new RecentlyViewedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyViewedViewHolder holder, final int position) {

        holder.name.setText(recentlyViewedList.get(position).getName());
//        holder.description.setText(recentlyViewedList.get(position).getDescription());

        DecimalFormat formatter = new DecimalFormat("#,###,###");

        holder.price.setText(formatter.format(recentlyViewedList.get(position).getPrice()) + "đ");

        FlexboxLayoutManager.LayoutParams layoutParams = (FlexboxLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        layoutParams.setFlexGrow(1);
//        layoutParams.
//        layoutParams.set(AlignItems.FLEX_START);
//        layoutParams.set
        holder.itemView.setLayoutParams(layoutParams);
//        holder.unit.setText(recentlyViewedList.get(position).get());
//        holder.bg.setBackgroundResource(R.drawable.no_img);
//        Log.d("img","http://localhost:3000/image/" + recentlyViewedList.get(position).getImg_url());
        try {
            Picasso.get()
                    .load(Api.url + "/image/" + recentlyViewedList.get(position).getImg_url())
                    .fit()
                    .error(R.drawable.no_img)
                    .into(holder.bg);

        }catch (Exception e){
            Log.d("img", e.toString());
        }
//        holder.bg.setImageBitmap(recentlyViewedList.get(position).getBitmap());
        holder.bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, ProductDetails.class);
                i.putExtra("id", String.valueOf(recentlyViewedList.get(position).getProduct_id()));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recentlyViewedList.size();
    }

    public  static class RecentlyViewedViewHolder extends RecyclerView.ViewHolder{

        TextView name, description, price, qty, unit;
        ImageView bg;

        public RecentlyViewedViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            unit = itemView.findViewById(R.id.unit);
            bg = itemView.findViewById(R.id.product_img);

        }
    }

    public void setList(List<PRODUCT> recentlyViewedList) {
        this.recentlyViewedList = recentlyViewedList;
    }
}
