package com.daklod.techshop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daklod.techshop.DTO.INVOICE_DETAIL;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.R;
import com.daklod.techshop.controller.Api;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.ThanhToanViewHolder> {
    Context context;
    List<PRODUCT> productList;
    List<INVOICE_DETAIL> details;

    public ThanhToanAdapter(Context context,List<PRODUCT> productList,List<INVOICE_DETAIL> details){
        this.context = context;
        this.productList = productList;
        this.details = details;
    }
    @NonNull
    @Override
    public ThanhToanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_thanhtoan, parent, false);

        return new ThanhToanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhToanViewHolder holder, int position) {
        holder.tieuDe.setText(productList.get(position).getName());
        NumberFormat money = NumberFormat.getCurrencyInstance();
        money.setMaximumFractionDigits(0);
        money.setCurrency(Currency.getInstance("VND"));

        DecimalFormat formatter = new DecimalFormat("#,###,###");

        holder.donGia.setText(formatter.format(productList.get(position).getPrice()) + "đ");
        holder.SLSanpham.setText(details.get(position).getAmount()+"");

        holder.tongGiaMoiSP.setText(formatter.format(productList.get(position).getPrice()*details.get(position).getAmount()) + "đ");
        try {
            Picasso.get()
                    .load(Api.url +"/image/"+ productList.get(position).getImg_url())
                    .fit()
                    .error(R.drawable.no_img)
                    .into(holder.anhSp);

        }catch (Exception e){
            Log.d("img", e.toString());
        }
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
    public class ThanhToanViewHolder extends RecyclerView.ViewHolder {
        ImageView anhSp;
        TextView tieuDe,donGia,SLSanpham,tongGiaMoiSP;

        public ThanhToanViewHolder(@NonNull View itemView) {
            super(itemView);
            anhSp = itemView.findViewById(R.id.pic);
            tieuDe = itemView.findViewById(R.id.titleTxt);
            donGia = itemView.findViewById(R.id.feeEachItem);
            SLSanpham = itemView.findViewById(R.id.numberItem);
            tongGiaMoiSP = itemView.findViewById(R.id.totalEachCart);
        }
    }
}
