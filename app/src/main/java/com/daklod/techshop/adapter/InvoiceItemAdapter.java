package com.daklod.techshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class InvoiceItemAdapter extends RecyclerView.Adapter<InvoiceItemAdapter.InvoiceViewHolder> {
    CartFragment fragment;
    List<PRODUCT> productList;
    List<INVOICE_DETAIL> invoiceDetail;
    int amount;
    Context context;

    public InvoiceItemAdapter(CartFragment fragment, List<PRODUCT> productList, List<INVOICE_DETAIL> invoiceDetail) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.productList = productList;
        this.invoiceDetail = invoiceDetail;
    }

    @NonNull
    @Override
    public InvoiceItemAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cart, parent, false);
        return new InvoiceItemAdapter.InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceItemAdapter.InvoiceViewHolder holder, final int position) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        holder.product = productList.get(position);
        holder.detail = invoiceDetail.get(position);
        holder.txtProductName.setText(productList.get(position).getName());
        holder.txtProductPrice.setText(formatter.format(productList.get(position).getPrice())+"đ");
        holder.txtProductTotal.setText(formatter.format(productList.get(position).getPrice()*invoiceDetail.get(position).getAmount()) + "đ");
        Picasso.get()
                .load("http://daklod-backend.vercel.app/image/" + productList.get(position).getImg_url())
                .fit()
                .error(R.drawable.no_img)
                .into(holder.imgProduct);

        holder.txtProductAmount.setText("x" + invoiceDetail.get(position).getAmount());

        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, ProductDetails.class);
                i.putExtra("id", String.valueOf(productList.get(position).getProduct_id()));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return invoiceDetail.size();
    }

    public static class InvoiceViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView txtProductName, txtProductPrice, txtProductAmount, txtProductInvoiceAmount, txtInvoicePrice, txtProductTotal;
        PRODUCT product;
        INVOICE_DETAIL detail;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.product_name);
           txtProductAmount = itemView.findViewById(R.id.product_amount);
           txtProductPrice = itemView.findViewById(R.id.product_price);
           txtProductInvoiceAmount = itemView.findViewById(R.id.txtAmountInvoice);
           txtInvoicePrice = itemView.findViewById(R.id.txtToalInvoice);
            txtProductTotal = itemView.findViewById(R.id.product_total);
        }

    }
}
