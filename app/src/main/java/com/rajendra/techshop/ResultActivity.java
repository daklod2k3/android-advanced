package com.rajendra.techshop;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ResultActivity extends AppCompatActivity {
    private ImageView back;
    private EditText inputResult;
    private Button btnName;
    private Button btnPrice;
    private int stateName = 0;
    private int statePrice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        back = findViewById(R.id.back4);
        inputResult = findViewById(R.id.editTextSearch4);
        btnName = findViewById(R.id.sortName);
        btnPrice = findViewById(R.id.sortPrice);
        inputResult.setFocusableInTouchMode(false);

        Intent intent = getIntent();
        String data = "";

        if (intent != null && intent.hasExtra("search")) {
            data = intent.getStringExtra("search");
            if (data != null) {
                inputResult.setText(data);
            }
        }

        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSortName();
                sortName();
            }
        });

        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortPrice();
                handleSortPrice();
            }
        });

        inputResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this, SearchActivity.class);
                i.putExtra("search", inputResult.getText().toString());
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void sortName() {
        if(stateName == 1 || stateName == 0) {
            Drawable doubleArrowDrawable = ContextCompat.getDrawable(this, R.drawable.sort_up);
            btnName.setCompoundDrawablesWithIntrinsicBounds(null, null, doubleArrowDrawable, null);
            stateName = 2;
        } else {
            Drawable doubleArrowDrawable = ContextCompat.getDrawable(this, R.drawable.sort_down);
            btnName.setCompoundDrawablesWithIntrinsicBounds(null, null, doubleArrowDrawable, null);
            stateName = 1;
        }
    }

    public void sortPrice() {
        if(statePrice == 1 || statePrice == 0) {
            Drawable doubleArrowDrawable = ContextCompat.getDrawable(this, R.drawable.sort_up);
            btnPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, doubleArrowDrawable, null);
            statePrice = 2;
        } else {
            Drawable doubleArrowDrawable = ContextCompat.getDrawable(this, R.drawable.sort_down);
            btnPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, doubleArrowDrawable, null);
            statePrice = 1;
        }
    }

    public void handleSortName() {
        if(stateName == 1) {

        } else if(stateName == 2) {

        }
    }
    public void handleSortPrice() {
        if(statePrice == 1) {

        } else if(statePrice == 2) {

        }
    }
}
