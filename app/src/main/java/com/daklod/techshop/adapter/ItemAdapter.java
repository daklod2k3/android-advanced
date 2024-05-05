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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private List<Item> itemList;

    public ItemAdapter(Context context, List<Item> itemList) {
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

        holder.txtProductName.setText(item.getNameProduct());
        holder.txtAmount.setText("x" + String.valueOf(item.getAmount()));
        holder.txtPrice.setText(String.format("%2.f đ", item.getPrice()));
        Picasso.get().load(item.getImgurl()).placeholder(R.drawable.b1).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtAmount, txtPrice;
        ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.item_tenspchitiet);
            txtAmount = itemView.findViewById(R.id.item_soluongchitiet);
            txtPrice = itemView.findViewById(R.id.item_giachitiet);
            imageView = itemView.findViewById(R.id.item_imgchitiet);
        }
    }
}
