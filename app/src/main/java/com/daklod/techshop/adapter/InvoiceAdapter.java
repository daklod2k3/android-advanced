package com.daklod.techshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.daklod.techshop.DTO.INVOICE;
import com.daklod.techshop.R;
import com.daklod.techshop.controller.InvoiceApi;

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
        getStatusName(invoice.getStatus_id(), new InvoiceApi.StatusNameCallback() {
            @Override
            public void onStatusNameReceived(String statusName) {
                holder.txtStatus.setText("Trạng thái: " + statusName);
            }

            @Override
            public void onStatusNameError(String errorMessage) {
                holder.txtStatus.setText("Trạng thái: Unknown");
            }
        });
        // Set up RecyclerView for item details
        if (invoice.getItem() != null && !invoice.getItem().isEmpty()) {
            ItemAdapter itemAdapter = new ItemAdapter(context, invoice.getItem());
            holder.recyclerView.setAdapter(itemAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }

    private void getStatusName(int statusId, InvoiceApi.StatusNameCallback statusNameCallback) {
        InvoiceApi invoiceApi = new InvoiceApi();
        invoiceApi.getStatusNameById(statusId, new InvoiceApi.StatusNameCallback() {
            @Override
            public void onStatusNameReceived(String statusName) {
                statusNameCallback.onStatusNameReceived(statusName);
            }

            @Override
            public void onStatusNameError(String errorMessage) {
                statusNameCallback.onStatusNameError(errorMessage);
            }
        });
    }


    public static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        TextView txtInvoice, txtStatus;
        RecyclerView recyclerView;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInvoice = itemView.findViewById(R.id.txtInvoiceId);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            recyclerView = itemView.findViewById(R.id.recycleview_chitiet);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}
