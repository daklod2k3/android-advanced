package com.daklod.techshop.adapter;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.daklod.techshop.databinding.ActivityQrcodeBinding;

import com.daklod.techshop.R;

public class QRcodeActivity extends AppCompatActivity {

private ActivityQrcodeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityQrcodeBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());


    }
}