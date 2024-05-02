package com.rajendra.techshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.techshop.DTO.INVOICE;
import com.rajendra.techshop.InvoiceDetailActivity;
import com.rajendra.techshop.R;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    private Context context;
    private List<INVOICE> invoiceList;

    public InvoiceAdapter(Context context, List<INVOICE> invoiceList) {
        this.context = context;
        this.invoiceList = invoiceList;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_row_item, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        INVOICE invoice = invoiceList.get(position);
        holder.txtInvoice.setText("Đơn hàng: " + invoice.getInvoice_id());
        holder.txtStatus.setText(invoice.getStatus_id() + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến InvoiceDetailActivity và gửi đối tượng INVOICE
                Intent intent = new Intent(context, InvoiceDetailActivity.class);
                intent.putExtra("invoice", (CharSequence) invoice);
                context.startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.reChiTiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(invoice.getItem().size());
        holder.reChiTiet.setLayoutManager(layoutManager);



        //adapter chitiet
        InvoiceDetailAdapter invoiceDetailAdapter = new InvoiceDetailAdapter(context, invoice.getItem());
        holder.reChiTiet.setAdapter(invoiceDetailAdapter);
    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }

//    public void setInvoices(List<INVOICE> invoices) {
//        this.invoiceList = invoices;
//        notifyDataSetChanged();
//    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder {
        TextView txtInvoice, txtStatus;
        RecyclerView reChiTiet;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInvoice = itemView.findViewById(R.id.idinvoice);
            txtStatus = itemView.findViewById(R.id.invoicestatus);
            reChiTiet = itemView.findViewById(R.id.recycleview_chitiet);
        }
    }
}
