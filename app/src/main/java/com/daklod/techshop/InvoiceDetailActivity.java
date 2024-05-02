package com.rajendra.techshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.techshop.DTO.INVOICE;
import com.rajendra.techshop.adapter.InvoiceDetailAdapter;

public class InvoiceDetailActivity extends AppCompatActivity {
    private TextView txtInvoiceId;
    private RecyclerView recyclerView;
    private InvoiceDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);

        txtInvoiceId = findViewById(R.id.txt_invoice_id);
        recyclerView = findViewById(R.id.recycler_view_invoice_detail);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            INVOICE invoice = (INVOICE) intent.getSerializableExtra("invoice");

            if (invoice != null) {
                txtInvoiceId.setText("Invoice ID: " + invoice.getInvoice_id());

                // Khởi tạo và cài đặt Adapter cho RecyclerView
                adapter = new InvoiceDetailAdapter(this, invoice.getItem());
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            }
        }
    }
}
