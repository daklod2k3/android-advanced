package com.daklod.techshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.daklod.techshop.DTO.INVOICE;
import com.daklod.techshop.adapter.InvoiceItemAdapter;
import com.daklod.techshop.adapter.ProductViewAdapter;
import com.daklod.techshop.controller.InvoiceAPI;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceHistory extends AppCompatActivity {

    String TAG = "InvoiceHistory";
    View loadView;
    BottomNavigationView navigationView;
    RecyclerView recyclerView;
    int status;
    List<INVOICE> invoiceList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_history);
        Log.d(TAG, "onCreate: ");
        status = getIntent().getIntExtra("status", 1);

        loadView = findViewById(R.id.loadingAnimation);
        navigationView = findViewById(R.id.invoiceTypeNavigation);
        recyclerView = findViewById(R.id.recycleInvoiceHistory);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.menu1:
                        setStatus(1);
                        break;
                    case R.id.menu2:
                        setStatus(2);
                        break;
                    case R.id.menu3:
                        setStatus(3);
                        break;

                }

                return true;

            }
        });

        new LoadInvoice().execute();

    }

    void setLoad(boolean load){
        if (load){
            loadView.setVisibility(View.VISIBLE);
            return;
        }
        loadView.setVisibility(View.GONE);
    }

    private void setAdapter(List<INVOICE> invoices){
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getBaseContext(), FlexDirection.ROW);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new InvoiceItemAdapter(getApplication(), invoices));
//        recyclerView.setAdapter(recentlyViewedAdapter);
    }

    private void setStatus(int status){
        List<INVOICE> tmp = new ArrayList<>();
        for (INVOICE invoice: invoiceList){
            if (invoice.getStatus_id() == status)
                tmp.add(invoice);
        }
        setAdapter(tmp);
    }



    class LoadInvoice extends AsyncTask<Void, String, List<INVOICE>>{
        @Override
        protected List<INVOICE> doInBackground(Void... voids) {
            try {
                return new InvoiceAPI().getInvoice().body();

            }catch (IOException e){
                publishProgress("Lỗi kết nối!");
            }catch (Exception e){
                publishProgress(e.toString());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(getBaseContext(), values[0], Toast.LENGTH_SHORT).show();
            cancel(true);
        }

        @Override
        protected void onPostExecute(List<INVOICE> invoices) {
            invoiceList = invoices;
            Collections.reverse(invoiceList);
//            setAdapter(invoices);
            switch (status){
                case 1:
                    navigationView.setSelectedItemId(R.id.menu1);
                    break;
                case 2:
                    navigationView.setSelectedItemId(R.id.menu2);
                    break;
                case 3:
                    navigationView.setSelectedItemId(R.id.menu3);
                    break;
            }
            setLoad(false);
        }
    }

}
