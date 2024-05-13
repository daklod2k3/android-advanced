package com.daklod.techshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.daklod.techshop.view.CartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.daklod.techshop.controller.CheckAuthAPI;
import com.daklod.techshop.view.HomeFragment;
import com.daklod.techshop.view.SampleFragment;

public class MainActivity extends AppCompatActivity {

    static String TAG = "MainActivity";
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    CartFragment cartFragment;

    SampleFragment sampleFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        Intent main = getIntent();
        if (main.getStringExtra("admin") == null)
            try {
                new CheckAuthTask().execute();
            }catch (Exception e){
                Log.e(TAG, "onCreate: ", e);
            }

    }

    private void initUI(){
        ReplaceFragment(new HomeFragment());

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d("fragment", String.valueOf(item.getItemId()));
            switch (item.getItemId()){
                case R.id.home:
                    if (homeFragment == null)
                        homeFragment = new HomeFragment();
                    ReplaceFragment(homeFragment);
                    break;
                case R.id.personal:
                    if (sampleFragment == null)
                        sampleFragment = new SampleFragment();
                    ReplaceFragment(sampleFragment);
                    break;

                case R.id.cart:
                    if (cartFragment == null)
                        cartFragment = new CartFragment();
                    ReplaceFragment(cartFragment);
                    break;
            }
            return  true;
        });
    }

    private void ReplaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_layout, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

//    private void ReplaceFragment(Fragment fragment){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_layout, fragment);
//        fragmentTransaction.commit();
//    }

    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    class CheckAuthTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... values) {
            try {
                return new CheckAuthAPI().checkAuth();
            }catch (Exception e){
                Log.d(TAG, "doInBackground: ");
                publishProgress();
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            CheckAuthAPI.clearToken(getBaseContext());
            alertLogout("Lỗi máy chủ", "Bạn sẽ trở về màn hình đăng nhập");
            cancel(true);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d(TAG, "onPostExecute: " + aBoolean);
            if (aBoolean) {
//                initUI();
                return;
            };
//            new Intent(getBaseContext(), LoginActivity.class);
//            startActivity();
            CheckAuthAPI.clearToken(getBaseContext());
            alertLogout("Thông tin đăng nhập hết hạn", "Bạn sẽ trở về màn hình đăng nhập");

        }
    }

    private void alertLogout(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
        //set icon
        .setIcon(android.R.drawable.ic_dialog_alert)
        //set title
        .setTitle(title)
        //set message
        .setMessage(message)
        //set positive button
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //set what would happen when positive button is clicked
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        }).setCancelable(false).show();
    }



}
