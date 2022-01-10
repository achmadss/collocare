package com.susiapp.collocare.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.susiapp.collocare.Async.GetQuiz;
import com.susiapp.collocare.Async.GetStory;
import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.HttpHandler;
import com.susiapp.collocare.R;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    Button buttonLetsGo;
    Globals globals = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d("", getExternalCacheDir().toString());
        // shared pref
        prefs = getSharedPreferences("com.example.collocare", MODE_PRIVATE);

//        Toast.makeText(this, "ASDF", Toast.LENGTH_SHORT).show();

        // handler
        connectAndGetData();

//        boolean a = isNetworkAvailable();
//        if(a) {
//            Toast.makeText(this, "ADA", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "GAADA", Toast.LENGTH_SHORT).show();
//        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void connectAndGetData() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                try {
                    String resultStory = new GetStory().execute().get();
                    String resultQuiz = new GetQuiz().execute().get();
//                    Log.e("", " + " + resultQuiz);
//                    Toast.makeText(SplashActivity.this, " + " + resultQuiz, Toast.LENGTH_SHORT).show();
                    if(resultStory.equals("success") || resultQuiz.equals("success")) {
                        if(prefs.getBoolean("firstrun", true)) {
                            setContentView(R.layout.activity_first_time);
                            buttonLetsGo = findViewById(R.id.letsgo);
                            prefs = getSharedPreferences("com.example.collocare", MODE_PRIVATE);
                            buttonLetsGo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    prefs.edit().putBoolean("firstrun", false).apply();
                                    checkLogin();
                                }
                            });
                        } else {
                            checkLogin();
                        }
                    } else {
                        Toast.makeText(SplashActivity.this, "Cannot get data", Toast.LENGTH_SHORT).show();
                        connectAndGetData();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 3500);
    }

    void checkLogin() {
        if(prefs.getBoolean("isLoggedIn", false)) {
            globals.setUsername(prefs.getString("username", ""));
            navigateToHome();
        } else {
            navigateToLoginRegister();
        }
    }

    void navigateToHome() {
        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    void navigateToLoginRegister() {
        Intent i = new Intent(SplashActivity.this, LoginRegisterActivity.class);
        startActivity(i);
        finish();
    }

}
