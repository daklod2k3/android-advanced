package com.rajendra.techshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.utils.Utils;
import com.google.gson.annotations.Until;
import com.rajendra.techshop.DTO.INVOICE;
import com.rajendra.techshop.adapter.InvoiceAdapter;
import com.rajendra.techshop.controller.InvoiceApi;
import com.rajendra.techshop.model.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HistoryFragment extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView reInvoice;
    private Toolbar toolbar;
    private InvoiceApi invoiceApi;
    private InvoiceAdapter invoiceAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_invoice);
        initView();
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Laptop", 2, "https://example.com/laptop.jpg"));
        itemList.add(new Item("Smartphone", 1, "https://example.com/smartphone.jpg"));

        INVOICE invoice = new INVOICE(1, 1, 1, "100.00", 123, new Date(), itemList);
        List<INVOICE> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);
        invoiceAdapter = new InvoiceAdapter(this, invoiceList);
        reInvoice.setAdapter(invoiceAdapter);

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
