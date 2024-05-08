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
import com.daklod.techshop.model.Item;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    private Context context;
    private List<INVOICE> invoiceList;
    private InvoiceApi invoiceApi;
    private OnItemClickListener listener;

    public InvoiceAdapter(Context context, List<INVOICE> invoiceList, OnItemClickListener listener) {
        this.context = context;
        this.invoiceList = invoiceList;
        this.invoiceApi = new InvoiceApi();
        this.listener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
        holder.bindInvoiceData(invoice);
    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder {
        private TextView txtInvoice, txtStatus;
        private RecyclerView recyclerView;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInvoice = itemView.findViewById(R.id.txtInvoiceId);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            recyclerView = itemView.findViewById(R.id.recycleview_chitiet);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        public void bindInvoiceData(INVOICE invoice) {
            txtInvoice.setText("Đơn hàng: " + invoice.getInvoice_id());

            // Gọi API để lấy tên trạng thái và hiển thị lên TextView
            invoiceApi.getStatusNameById(invoice.getStatus_id(), new InvoiceApi.StatusNameCallback() {
                @Override
                public void onStatusNameReceived(String statusName) {
                    txtStatus.setText("Trạng thái: " + statusName);
                }

                @Override
                public void onStatusNameError(String errorMessage) {
                    txtStatus.setText("Trạng thái: Không rõ");
                }
            });

            // Hiển thị danh sách mặt hàng trong hóa đơn
            bindItemRecyclerView(invoice.getItems());
        }

        private void bindItemRecyclerView(List<Item> itemList) {
            if (itemList != null && !itemList.isEmpty()) {
                ItemAdapter itemAdapter = new ItemAdapter(context, itemList);
                recyclerView.setAdapter(itemAdapter);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(INVOICE invoice);
    }
}
