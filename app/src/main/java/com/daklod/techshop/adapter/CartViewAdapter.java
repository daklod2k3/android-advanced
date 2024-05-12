package com.daklod.techshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.daklod.techshop.DTO.INVOICE_DETAIL;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.ProductDetails;
import com.daklod.techshop.R;
import com.daklod.techshop.controller.CartAPI;
import com.daklod.techshop.view.CartFragment;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.CartViewHolder> {
    CartFragment fragment;
    List<PRODUCT> productList;
    List<INVOICE_DETAIL> invoiceDetail;
    int amount;
    Context context;

    public CartViewAdapter(CartFragment fragment, List<PRODUCT> productList, List<INVOICE_DETAIL> invoiceDetail) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.productList = productList;
        this.invoiceDetail = invoiceDetail;
    }

    @NonNull
    @Override
    public CartViewAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cart, parent, false);
        return new CartViewAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewAdapter.CartViewHolder holder, final int position) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        holder.product = productList.get(position);
        holder.detail = invoiceDetail.get(position);
        holder.title.setText(productList.get(position).getName());
        holder.price.setText(formatter.format(productList.get(position).getPrice())+"đ");
        holder.totalPrice.setText(formatter.format(productList.get(position).getPrice()*invoiceDetail.get(position).getAmount()) + "đ");
        Picasso.get()
                .load("http://daklod-backend.vercel.app/image/" + productList.get(position).getImg_url())
                .fit()
                .error(R.drawable.no_img)
                .into(holder.img);
        holder.img.setImageBitmap(productList.get(position).getBitmap());
        holder.numberItem.setText(invoiceDetail.get(position).getAmount()+"");

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, ProductDetails.class);
                i.putExtra("id", String.valueOf(productList.get(position).getProduct_id()));
                context.startActivity(i);
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CartFragment.ChangeAmountTask(fragment).execute(new CartAPI.AddCartBody(holder.product.getProduct_id(), - 1));
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.product.getAmount() < holder.detail.getAmount() + 1)
                    Toast.makeText(view.getContext(), "Đã đạt giới hạn số lượng", Toast.LENGTH_SHORT).show();
                else
                    new CartFragment.ChangeAmountTask(fragment).execute(new CartAPI.AddCartBody(holder.product.getProduct_id(),  1));

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CartFragment.ChangeAmountTask(fragment).execute(new CartAPI.AddCartBody(holder.product.getProduct_id(),  holder.detail.getAmount() * -1));
            }
        });

    }

    @Override
    public int getItemCount() {
        return invoiceDetail.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView img, delete;
        TextView title, price, totalPrice, minus, plus, numberItem;
        PRODUCT product;
        INVOICE_DETAIL detail;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgOrdered);
            numberItem = itemView.findViewById(R.id.numberItem);
            delete = itemView.findViewById(R.id.imgDelete);
            title = itemView.findViewById(R.id.txtTitle);
            price = itemView.findViewById(R.id.EachItemPrice);
            totalPrice = itemView.findViewById(R.id.txtPriceTotal);
            minus = itemView.findViewById(R.id.minusItem);
            plus = itemView.findViewById(R.id.plusItem);
        }

    }
}
