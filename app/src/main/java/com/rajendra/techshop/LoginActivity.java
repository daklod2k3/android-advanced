package com.rajendra.techshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.rajendra.techshop.DTO.USER;
import com.rajendra.techshop.controller.LoginAPI;
import com.rajendra.techshop.databinding.ActivityLoginBinding;

import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity {
    String TAG = "Login Activity";

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setActivated(true);

        binding.btnLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!v.isActivated()) return false;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        v.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        String username = binding.txtEditUser.getText().toString().trim();
                        String password = binding.txtEditPass.getText().toString().trim();
                        if (username.equals("admin") && password.equals("admin"))
                            startActivity(new Intent(getApplication(), MainActivity.class));

                        new LoginTask().execute(new LoginAPI.LoginBody(username, password));
                        v.setActivated(false);
                        break;
                    }
                        // Your action here on button click
                    case MotionEvent.ACTION_UP: {
                        break;
                    }
                }
                return false;
            }
        });


        binding.txtEditUser.setText("admin");
        binding.txtEditPass.setText("admin");



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }

    // HANDEL OUT FORCUS EDITTEXT

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    class LoginTask extends AsyncTask<LoginAPI.LoginBody, Exception, USER>{
        @Override
        protected USER doInBackground(LoginAPI.LoginBody... loginInfos) {
            try {
                return (new LoginAPI().postLogin(loginInfos[0]));
            }catch (Exception e){
                publishProgress(e);
                cancel(false);
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Exception... values) {
            if (values[0] instanceof IOException){
                Toast.makeText(getBaseContext(), "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                binding.btnLogin.setActivated(true);
                binding.btnLogin.getBackground().clearColorFilter();
                binding.btnLogin.invalidate();
                return;
            }

            Toast.makeText(getBaseContext(), values[0].toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(USER user) {
            binding.btnLogin.setActivated(true);
            binding.btnLogin.getBackground().clearColorFilter();
            binding.btnLogin.invalidate();
            if (user == null){
                Toast.makeText(getBaseContext(), "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(getApplication(), MainActivity.class));
        }
    }



}