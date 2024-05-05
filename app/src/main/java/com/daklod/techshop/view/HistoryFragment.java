package com.daklod.techshop.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daklod.techshop.DTO.INVOICE;
import com.daklod.techshop.R;
import com.daklod.techshop.adapter.InvoiceAdapter;
import com.daklod.techshop.controller.InvoiceApi;

import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private InvoiceAdapter invoiceAdapter;
    private InvoiceApi invoiceApi;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.invoiceRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        invoiceApi = new InvoiceApi();

        // Gọi loadInvoices trên luồng phụ để tránh chặn luồng chính (UI thread)
        new LoadInvoicesTask().execute();
    }

    private class LoadInvoicesTask extends AsyncTask<Void, Void, List<INVOICE>> {

        @Override
        protected List<INVOICE> doInBackground(Void... voids) {
            // Gọi API để lấy danh sách đơn hàng trên luồng phụ
            return invoiceApi.getAllInvoices();
        }

        @Override
        protected void onPostExecute(List<INVOICE> invoices) {
            super.onPostExecute(invoices);

            if (invoices != null) {
                // Hiển thị danh sách đơn hàng bằng cách khởi tạo Adapter và đặt Adapter cho RecyclerView
                invoiceAdapter = new InvoiceAdapter(getContext(), invoices);
                recyclerView.setAdapter(invoiceAdapter);
            } else {
                // Hiển thị thông báo lỗi nếu không thể tải danh sách đơn hàng
                Toast.makeText(getContext(), "Failed to load invoices", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
