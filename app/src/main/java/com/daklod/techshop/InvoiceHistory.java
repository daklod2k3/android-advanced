package com.daklod.techshop;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.daklod.techshop.adapter.InvoiceItemAdapter;
import com.daklod.techshop.adapter.ProductViewAdapter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class InvoiceHistory extends AppCompatActivity {

    View loadView;
    BottomNavigationView navigationView;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_invoice_history);

        loadView = findViewById(R.id.loadingAnimation);
        navigationView = findViewById(R.id.invoiceTypeNavigation);
        recyclerView = findViewById(R.id.recycleInvoiceHistory);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.menu1:
                        setAdapter(1);
                        break;
                    case R.id.menu2:
                        setAdapter(2);
                        break;
                    case R.id.menu3:
                        setAdapter(3);
                        break;

                }

                return false;

            }
        });

    }

    void setLoad(boolean load){
        if (load){
            loadView.setVisibility(View.VISIBLE);
            return;
        }
        loadView.setVisibility(View.GONE);
    }

    private void setAdapter(int status){
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getBaseContext(), FlexDirection.COLUMN);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
//        layoutManager.d
//        layoutManager.set
//        layoutManager.scroll;
//        layoutManager.set
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView = new InvoiceItemAdapter(getBaseContext(), new );
//        recyclerView.setAdapter(recentlyViewedAdapter);
    }

    public static class InvoiceHistoryDataAdapter{
        public InvoiceHistoryDataAdapter(){

        }
    }


}
