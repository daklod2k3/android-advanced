package com.daklod.techshop.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daklod.techshop.AllCategory;
import com.daklod.techshop.DTO.CUSTOMER;
import com.daklod.techshop.DTO.USER;
import com.daklod.techshop.InvoiceHistory;
import com.daklod.techshop.LoginActivity;
import com.daklod.techshop.R;
import com.daklod.techshop.SearchActivity;
import com.daklod.techshop.controller.Api;
import com.daklod.techshop.controller.CheckAuthAPI;
import com.daklod.techshop.controller.LoginAPI;
import com.daklod.techshop.databinding.FragmentSampleBinding;


public class SampleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btn;
    TextView username;
    FragmentSampleBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn = getView().findViewById(R.id.btnLogout);
        username = getView().findViewById(R.id.username);
        CUSTOMER user = CheckAuthAPI.getUserFromToken();
        if (user != null){
            username.setText(user.getName());
        }
        else{
            username.setText("Null");
        }

        binding.btnInvoiceHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                Intent intent = new Intent(getContext(), InvoiceHistory.class);
                intent.putExtra("status", 1);
                startActivity(intent);
            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAuthAPI.clearToken(getContext());
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sample, container, false);
        binding = FragmentSampleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}