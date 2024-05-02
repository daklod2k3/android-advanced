package com.rajendra.techshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.techshop.R;
import com.rajendra.techshop.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InvoiceViewAdapter extends RecyclerView.Adapter<InvoiceViewAdapter.InvoicelAdapterViewHolder> {
    Context context;
    List<Item> itemList;


    public InvoiceViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public InvoicelAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_item_detail, parent, false);
        return new InvoicelAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoicelAdapterViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtName.setText(item.getNameProduct() + "");
        holder.txtAmount.setText("Số lượng: " + item.getAmount());
        Picasso.get().load(item.getImgurl()).into(holder.imageInvoiceDetail);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class InvoicelAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageInvoiceDetail;
        TextView txtName, txtAmount;

        public InvoicelAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_tenspchitiet);
            txtAmount = itemView.findViewById(R.id.item_soluongchitiet);
            imageInvoiceDetail = itemView.findViewById(R.id.item_imgchitiet);
        }
    }
}
