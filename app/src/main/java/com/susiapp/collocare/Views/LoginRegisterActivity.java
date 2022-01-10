package com.susiapp.collocare.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.susiapp.collocare.Async.Login;
import com.susiapp.collocare.Async.Register;
import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.R;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginRegisterActivity extends AppCompatActivity {

    Globals globals = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = findViewById(R.id.login_username);
        final EditText password = findViewById(R.id.login_password);
        final Button signIn = findViewById(R.id.login_btn);
        Button register = findViewById(R.id.register_btn);


        final SharedPreferences prefs = getSharedPreferences("com.example.collocare", MODE_PRIVATE);

        final ProgressBar loading = findViewById(R.id.login_loading);
        loading.setVisibility(View.INVISIBLE);
        loading.getIndeterminateDrawable()
                .setColorFilter(
                        getResources().getColor(R.color.white),
                        PorterDuff.Mode.SRC_IN);

        final LinearLayout layout = findViewById(R.id.login_layout);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString = username.getText().toString();
                String passwordString = password.getText().toString();
                if(usernameString.isEmpty() || passwordString.isEmpty()) {
                    Toast.makeText(LoginRegisterActivity.this, "Username and Password Required", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String result = new Login(usernameString, passwordString, prefs).execute().get();
                        Log.e("", result);
                        if(result.equals("success")) {
                            Toast.makeText(LoginRegisterActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            prefs.edit().putBoolean("isLoggedIn", true).apply();
                            globals.setUsername(usernameString);
                            createTxtFile(usernameString);
                            Intent i = new Intent(LoginRegisterActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(LoginRegisterActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString = username.getText().toString();
                String passwordString = password.getText().toString();
                if(usernameString.isEmpty() || passwordString.isEmpty()) {
                    Toast.makeText(LoginRegisterActivity.this, "Username and Password Required", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String result = new Register(usernameString, passwordString).execute().get();
                        Log.e("", result);
                        if(result.equals("success")) {
                            Toast.makeText(LoginRegisterActivity.this, "Register Success. Please sign in.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginRegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    void createTxtFile(String username) {
        try {
            File file = new File(globals.rootPath + "log_"+username+".txt");
            if (!file.exists()) {
                file.createNewFile();   // create file if not exist
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}