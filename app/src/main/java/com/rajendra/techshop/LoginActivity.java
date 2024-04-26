package com.rajendra.techshop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.rajendra.techshop.controller.CheckAuthAPI;
import com.rajendra.techshop.controller.LoginAPI;
import com.rajendra.techshop.databinding.ActivityLoginBinding;

import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@SuppressLint("ClickableViewAccessibility")
public class LoginActivity extends AppCompatActivity {
    String TAG = "Login Activity";
    private static Context context;

    ActivityLoginBinding binding;



    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = getBaseContext().getSharedPreferences(getString(R.string.auth_key_stored), Context.MODE_PRIVATE);
        Log.d(TAG, "onStart: " + sharedPref.getString(getString(R.string.auth_key_stored), null));
        String token = sharedPref.getString(getString(R.string.auth_key_stored), null);
        if (token != null){
            Intent main = new Intent(this, MainActivity.class);
            CheckAuthAPI.setAuthHeader(token);
            startActivity(main);
            finish();
        }
    }

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

                        if (binding.txtLayoutUser.isErrorEnabled() || binding.txtLayoutPass.isErrorEnabled())
                            return false;

                        v.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        v.setActivated(false);

                        String username = binding.txtEditUser.getText().toString().trim();
                        String password = binding.txtEditPass.getText().toString();

                        if (username.equals("admin") && password.equals("admin")){
                            Intent main = new Intent(getApplication(), MainActivity.class);
                            main.putExtra("admin", "admin");
                            startActivity(main);
                            return false;
                        }
                        new LoginTask().execute(new LoginAPI.LoginBody(username, password));
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


//        binding.txtEditUser.setText("admin");
//        binding.txtEditPass.setText("admin");

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

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), RegisterActivity.class));
            }
        });

        context = getBaseContext();

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

    class LoginTask extends AsyncTask<LoginAPI.LoginBody, Exception, CUSTOMER>{
        @Override
        protected CUSTOMER doInBackground(LoginAPI.LoginBody... loginInfos) {
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
        protected void onPostExecute(CUSTOMER user) {
            binding.btnLogin.setActivated(true);
            binding.btnLogin.getBackground().clearColorFilter();
            binding.btnLogin.invalidate();
            if (user == null){
                Toast.makeText(getBaseContext(), "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(getApplication(), MainActivity.class));
            finish();
        }
    }

    public static Context getAppContext(){
        return context;
    }


}