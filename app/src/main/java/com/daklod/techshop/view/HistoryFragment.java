package com.daklod.techshop.view;

import android.content.Intent;
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
import com.daklod.techshop.InvoiceDetailActivity;
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo RecyclerView và thiết lập LayoutManager
        recyclerView = view.findViewById(R.id.invoiceRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo đối tượng InvoiceApi để gọi API liên quan đến đơn hàng
        invoiceApi = new InvoiceApi();

        // Sử dụng AsyncTask để gọi API lấy danh sách đơn hàng trên luồng phụ
        new LoadInvoicesTask().execute();
    }

    // AsyncTask để gọi API lấy danh sách đơn hàng trong luồng phụ
    private class LoadInvoicesTask extends AsyncTask<Void, Void, List<INVOICE>> {

        @Override
        protected List<INVOICE> doInBackground(Void... voids) {
            // Gọi API để lấy danh sách đơn hàng trong luồng phụ
            return invoiceApi.getAllInvoices();
        }

        @Override
        protected void onPostExecute(List<INVOICE> invoices) {
            super.onPostExecute(invoices);

            if (invoices != null) {
                // Nếu danh sách đơn hàng không rỗng, khởi tạo Adapter và thiết lập Adapter cho RecyclerView
                invoiceAdapter = new InvoiceAdapter(getContext(), invoices, new InvoiceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(INVOICE invoice) {
                        // Xử lý sự kiện khi người dùng click vào đơn hàng
                        showInvoiceDetail(invoice);
                    }
                });
                recyclerView.setAdapter(invoiceAdapter);
            } else {
                // Hiển thị thông báo lỗi nếu không thể tải danh sách đơn hàng
                Toast.makeText(getContext(), "Failed to load invoices", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Phương thức để mở chi tiết đơn hàng khi người dùng click vào một đơn hàng trong RecyclerView
    private void showInvoiceDetail(INVOICE invoice) {
        Intent intent = new Intent(getActivity(), InvoiceDetailActivity.class);
        intent.putExtra("invoice", (CharSequence) invoice); // Truyền đối tượng INVOICE sang Activity mới
        startActivity(intent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // Kiểm tra kết quả trả về từ InvoiceDetailActivity
            if (resultCode == getActivity().RESULT_OK) {
                // Xử lý khi nhận được kết quả OK từ InvoiceDetailActivity
                String resultData = data.getStringExtra("result_key");
                Toast.makeText(getContext(), "Received result: " + resultData, Toast.LENGTH_SHORT).show();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // Xử lý khi nhận được kết quả RESULT_CANCELED từ InvoiceDetailActivity
                Toast.makeText(getContext(), "Activity canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
