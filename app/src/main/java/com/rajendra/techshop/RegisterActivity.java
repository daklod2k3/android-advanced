package com.rajendra.techshop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.rajendra.techshop.DTO.CUSTOMER;
import com.rajendra.techshop.DTO.USER;
import com.rajendra.techshop.controller.LoginAPI;
import com.rajendra.techshop.controller.RegisterAPI;
import com.rajendra.techshop.databinding.ActivityRegisterBinding;

import java.io.IOException;

import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@SuppressLint("ClickableViewAccessibility")
public class RegisterActivity extends AppCompatActivity {
    String TAG = "Register Activity";

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setActivated(true);

        binding.btnRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!v.isActivated()) return false;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{

                        String username = binding.txtEditUser.getText().toString().trim();
                        String password = binding.txtEditPass.getText().toString();
                        String email = binding.txtEditPass.getText().toString().trim();
                        String name = binding.txtEditName.getText().toString().trim();
                        String phone = binding.txtEditPhone.getText().toString().trim();
                        String confirm = binding.txtEditPassConfirm.getText().toString();

                        disableBtnRegister();

                        if (username.equals("") || password.equals("") || !password.equals(confirm) || name.equals("")){
                            Toast.makeText(getBaseContext(), "Thiếu thông tin", Toast.LENGTH_SHORT).show();
                            enableBtnRegister();
                            return false;
                        }
                        new RegisterTask().execute(new RegisterAPI.RegisterBody(name, username, phone, password, email));
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
        binding.txtEditUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    binding.txtLayoutUser.setError("Thiếu tài khoản");
                }else{
                    binding.txtLayoutUser.setError(null);
                    binding.txtLayoutUser.setErrorEnabled(false);
                }
            }
        });

        binding.txtEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    binding.txtLayoutUser.setError("Thiếu họ tên");
                }else{
                    binding.txtLayoutUser.setError(null);
                    binding.txtLayoutUser.setErrorEnabled(false);
                }
            }
        });

        binding.txtEditPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    binding.txtLayoutPass.setError("Thiếu mật khẩu");
                }else{
                    binding.txtLayoutPass.setError(null);
                    binding.txtLayoutPass.setErrorEnabled(false);
                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), LoginActivity.class));
            }
        });

        binding.txtEditPassConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!binding.txtEditPass.getText().toString().equals(s.toString()))
                    binding.txtLayoutPassConfirm.setError("Mật khẩu không trùng khớp!");
                else{
//                    binding.txtLayoutPassConfirm.setError(null);
                    binding.txtLayoutPassConfirm.setErrorEnabled(false);
                }
            }
        });
    }

    public void enableBtnRegister() {
        binding.btnRegister.setActivated(true);
        binding.btnRegister.getBackground().clearColorFilter();
        binding.btnRegister.invalidate();
    }

    public void disableBtnRegister(){
        binding.btnRegister.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        binding.btnRegister.invalidate();
        binding.btnRegister.setActivated(false);
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

    class RegisterTask extends AsyncTask<RegisterAPI.RegisterBody, String, CUSTOMER>{
        @Override
        protected CUSTOMER doInBackground(RegisterAPI.RegisterBody... registerBodies) {
            try {
                Response<CUSTOMER> response = new RegisterAPI().postRegister(registerBodies[0]);
                if (!response.isSuccessful()){
                    String error = response.errorBody().string();
                    if (error.contains("Username already exist")){
                        publishProgress("Tài khoản đã tồn tại");
                    }
                }

                return response.body();
            }catch (IOException e){
                publishProgress("Lỗi kết nối");
                return null;
            }catch (Exception e){
                publishProgress(e.toString());
                cancel(false);
                return null;
            }finally {
                enableBtnRegister();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(getBaseContext(), values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(CUSTOMER customer) {
            enableBtnRegister();
            if (customer == null) return;
            startActivity(new Intent(getApplication(), MainActivity.class));
            finish();
        }
    }



}