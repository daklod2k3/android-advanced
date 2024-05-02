package com.rajendra.techshop.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.techshop.DTO.INVOICE;
import com.rajendra.techshop.R;
import com.rajendra.techshop.adapter.InvoiceAdapter;
import com.rajendra.techshop.controller.InvoiceApi;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class HistoryFragment extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView reInvoice;
    private Toolbar toolbar;
    private InvoiceApi invoiceApi;
    private InvoiceAdapter invoiceAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);
        initView();
        initToolbar();
        invoiceApi = new InvoiceApi();
        getInvoice();
    }

    private void getInvoice() {
        List<INVOICE> invoices = invoiceApi.getAllInvoices();
        if (invoices != null) {
            if (invoiceAdapter == null) {
                invoiceAdapter = new InvoiceAdapter(this, invoices);
                reInvoice.setAdapter(invoiceAdapter);
            } else {
                invoiceAdapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, "Failed to retrieve invoices", Toast.LENGTH_SHORT).show();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initView() {
        reInvoice = findViewById(R.id.recycleview_chitiet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reInvoice.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}

