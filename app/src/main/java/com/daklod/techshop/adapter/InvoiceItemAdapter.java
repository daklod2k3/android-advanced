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

import com.daklod.techshop.DTO.INVOICE;
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
    String TAG = "InvoiceAdapter";
    List<INVOICE> invoices;
    int amount;
    Context context;

    public InvoiceItemAdapter(Context context, List<INVOICE> invoices) {
        this.context = context;
        this.invoices = invoices;
    }

    @NonNull
    @Override
    public InvoiceItemAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_item, parent, false);
        return new InvoiceItemAdapter.InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceItemAdapter.InvoiceViewHolder holder, final int position) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        INVOICE invoice = invoices.get(position);
        Log.d(TAG, "onBindViewHolder: " + invoice.getInvoice_id());
//        invoice.getDate_created().setHours(invoice.getDate_created().getHours() + 7);

        holder.txtInvoiceId.setText("Mã hoá đơn: " + invoice.getInvoice_id());
        holder.txtInvoiceDate.setText("Ngày mua: " + invoice.getDate_created().getDate() + "/" +invoice.getDate_created().getMonth() + "/" + (invoice.getDate_created().getYear() + 1900));
        holder.txtInvoiceTotal.setText("Thanh toán: " + formatter.format(invoice.getTotal_product()) + "đ");

        holder.txtAmountTotal.setText("Số lượng sản phẩm: " + invoice.getTotal_amount());

    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    public static class InvoiceViewHolder extends RecyclerView.ViewHolder{

        TextView txtInvoiceId, txtInvoiceTotal, txtAmountTotal, txtInvoiceDate;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInvoiceId = itemView.findViewById(R.id.txtInvoiceId);
            txtInvoiceDate = itemView.findViewById(R.id.txtInvoiceDate);
            txtInvoiceTotal = itemView.findViewById(R.id.txtInvoiceTotal);
            txtAmountTotal = itemView.findViewById(R.id.txtAmountTotal);
        }

    }
}
