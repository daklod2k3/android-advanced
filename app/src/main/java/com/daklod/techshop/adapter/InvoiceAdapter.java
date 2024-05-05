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

    // Khai báo interface callback để xử lý sự kiện khi item được nhấp vào
    public interface OnItemClickListener {
        void onItemClick(INVOICE invoice);
    }

    private Context context;
    private List<INVOICE> invoiceList;
    private OnItemClickListener listener;

    public InvoiceAdapter(Context context, List<INVOICE> invoiceList, OnItemClickListener listener) {
        this.context = context;
        this.invoiceList = invoiceList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout của một row item để tạo một ViewHolder mới
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_row_item, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        // Lấy thông tin của hóa đơn tại vị trí position
        INVOICE invoice = invoiceList.get(position);

        // Gán các giá trị vào các thành phần giao diện của ViewHolder
        holder.txtInvoice.setText("Đơn hàng: " + invoice.getInvoice_id());

        // Gọi API để lấy tên trạng thái dựa trên status_id của hóa đơn
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

        // Set up RecyclerView con để hiển thị danh sách các mặt hàng trong hóa đơn
        if (invoice.getItem() != null && !invoice.getItem().isEmpty()) {
            ItemAdapter itemAdapter = new ItemAdapter(context, invoice.getItem());
            holder.recyclerView.setAdapter(itemAdapter);
        }

        // Gán sự kiện click cho item view để xử lý khi item được nhấp vào
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi callback để xử lý sự kiện khi item được nhấp vào
                listener.onItemClick(invoice);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng hóa đơn trong danh sách
        return invoiceList.size();
    }

    // Phương thức để gọi API và lấy tên trạng thái dựa trên statusId
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

    // ViewHolder để ánh xạ các thành phần giao diện của row item
    public static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        TextView txtInvoice, txtStatus;
        RecyclerView recyclerView;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các thành phần giao diện từ row item layout
            txtInvoice = itemView.findViewById(R.id.txtInvoiceId);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            recyclerView = itemView.findViewById(R.id.recycleview_chitiet);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}
