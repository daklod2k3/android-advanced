package com.daklod.techshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daklod.techshop.R;
import com.daklod.techshop.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InvoiceDetailAdapter extends RecyclerView.Adapter<InvoiceDetailAdapter.ItemViewHolder> {

    private Context context;
    private List<Item> itemList;

    public InvoiceDetailAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_item_detail, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txtProductName, txtAmount, txtPrice;
        private ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.item_tenspchitiet);
            txtAmount = itemView.findViewById(R.id.item_soluongchitiet);
            txtPrice = itemView.findViewById(R.id.item_giachitiet);
            imageView = itemView.findViewById(R.id.item_imgchitiet);
        }

        public void bind(Item item) {
            txtProductName.setText(item.getNameProduct());
            txtAmount.setText("x" + item.getAmount());
            txtPrice.setText(String.format("%,.0f đ", item.getPrice()));

            // Load image from URL using Picasso
            loadImageFromUrl(item.getImgUrl());
        }

        private void loadImageFromUrl(String imageUrl) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get()
                        .load("http://daklod-backend.vercel.app/image/" + imageUrl)
                        .fit()
                        .error(R.drawable.no_img)
                        .into(imageView);
            } else {
                // Display placeholder if URL is empty or null
                imageView.setImageResource(R.drawable.no_img);
            }
        }
    }
}
