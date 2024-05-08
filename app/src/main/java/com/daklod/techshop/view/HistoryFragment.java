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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.invoiceRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        invoiceApi = new InvoiceApi();

        // Execute AsyncTask to load invoices
        new LoadInvoicesTask().execute();
    }

    private class LoadInvoicesTask extends AsyncTask<Void, Void, List<INVOICE>> {

        @Override
        protected List<INVOICE> doInBackground(Void... voids) {
            // Call method in InvoiceApi to fetch list of invoices
            final List<INVOICE>[] invoices = new List[] { null };
            invoiceApi.getAllInvoices(new InvoiceApi.InvoiceCallback<List<INVOICE>>() {
                @Override
                public void onSuccess(List<INVOICE> data) {
                    invoices[0] = data;
                }

                @Override
                public void onError(String errorMessage) {
                    // Show error message using Toast on UI thread
                    showToast(errorMessage);
                }
            });
            return invoices[0];
        }

        @Override
        protected void onPostExecute(List<INVOICE> invoices) {
            super.onPostExecute(invoices);
            if (invoices != null) {
                // If invoices list is not null, display it in RecyclerView using InvoiceAdapter
                invoiceAdapter = new InvoiceAdapter(getContext(), invoices, new InvoiceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(INVOICE invoice) {
                        showInvoiceDetail(invoice);
                    }
                });
                recyclerView.setAdapter(invoiceAdapter);
            } else {
                // If invoices list is null, show error message
                showToast("Failed to load invoices");
            }
        }

        private void showInvoiceDetail(INVOICE invoice) {
            // Display invoice detail when clicked
            Intent intent = new Intent(getActivity(), InvoiceDetailActivity.class);
            intent.putExtra("invoice", (CharSequence) invoice); // Pass INVOICE object to intent
            startActivity(intent);
        }

        private void showToast(final String message) {
            // Show Toast message on UI thread
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {
                String resultData = data.getStringExtra("result_key");
                Toast.makeText(getContext(), "Received result: " + resultData, Toast.LENGTH_SHORT).show();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                Toast.makeText(getContext(), "Activity canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
