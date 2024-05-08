package com.daklod.techshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.daklod.techshop.adapter.searchViewAdapter;
import com.daklod.techshop.model.Recent;

public class SearchActivity extends AppCompatActivity {
    private EditText inputSearch;
    private ListView list;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputSearch = findViewById(R.id.editTextSearch3);
        list = findViewById(R.id.listView1);
        back = findViewById(R.id.back3);

        Intent intent = getIntent();
        String data = "";
        if (intent != null && intent.hasExtra("search")) {
            data = intent.getStringExtra("search");
            if (data != null) {
                inputSearch.setText(data);
                inputSearch.setSelection(inputSearch.getText().length());
            }
        }

        list.setOnItemClickListener((parent, view, position, id) -> {
            if (!inputSearch.getText().equals("")) {
                String selectedItem = ((TextView) view).getText().toString();
                inputSearch.setText(selectedItem);
                Intent i = new Intent(SearchActivity.this, ResultActivity.class);
                i.putExtra("search", inputSearch.getText().toString());
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
        // List<String> newArray = itemList.subList(0, Math.min(itemList.size(), 5));
        // ArrayList<String> newArrayList = new ArrayList<>(newArray);
        // searchViewAdapter adapter1 = new searchViewAdapter(this, newArrayList);
        // list.setAdapter(adapter1);
        searchViewAdapter adapter = new searchViewAdapter(this, Recent.getData());
        list.setAdapter(adapter);
    }

    public void onSearchButtonClick(View view) {
        if (!inputSearch.getText().equals("")) {
            Recent.addData(inputSearch.getText().toString());
            Intent i = new Intent(SearchActivity.this, ResultActivity.class);
            i.putExtra("search", inputSearch.getText().toString());
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        inputSearch = findViewById(R.id.editTextSearch3);
        inputSearch.requestFocus();
        inputSearch.setSelection(inputSearch.getText().length());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
