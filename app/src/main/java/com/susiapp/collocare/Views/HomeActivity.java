package com.susiapp.collocare.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.susiapp.collocare.Adapters.HomeAdapter;
import com.susiapp.collocare.Models.Chapter;
import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.R;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    Globals globals = Globals.getInstance();
    ListView homeListView;
    HomeAdapter homeAdapter;
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeListView = findViewById(R.id.listview_home);
        homeAdapter = new HomeAdapter(getApplicationContext());
        homeListView.setAdapter(homeAdapter);

        prefs = getSharedPreferences("com.example.collocare", MODE_PRIVATE);

        TextView name = findViewById(R.id.username_text);
        name.setText(prefs.getString("username", ""));

        Button signOutBtn = findViewById(R.id.signOut_btn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().clear().apply();
                prefs.edit().putBoolean("isLoggedIn", false).apply();
                Intent i = new Intent(HomeActivity.this, LoginRegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        String[] creditsList = {
                "iconfinder.com", "123RF.com",
                "clipartmax.com", "imgur.com/user/shenanigansen",
                "DLPNG.com", "breakitdowndogtraining.com",
                "medium.com", "amazon.com",
                "istock.com", "hindustantimes.com",
                "motor1.com", "cornerstonerecoverycenter.com",
                "pinterest.com", "pinclipart.com",
                "dietdoctor.com", "vectorstock.com",
                "stickpng.com", "clipart-library.com",
                "worthingtonhistoricalsociety.org", "netclipart.com",
                "ny.eater.com", "matcha-jp.com",
                "freepik.com", "bukalapak.com", "wikiclipart.com",
                "gramener.com/comicgen"
        };

        StringBuilder creditsBuilder = new StringBuilder();
        for (int i = 0; i < creditsList.length; i++) {
            creditsBuilder.append(creditsList[i]);
            creditsBuilder.append("\n");
        }
        final String creditsCombined = creditsBuilder.toString();

        Button creditsBtn = findViewById(R.id.btn_credits);
        creditsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Credits")
                        .setMessage("Sumber materi :\nBuku \"English Collocations in Use\"\noleh Felicity O'Dell dan Michael McCarthy\n\nSumber gambar :\n"+creditsCombined)
                        .setCancelable(true)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
//                        .setNegativeButton(android.R.string.no, null)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        globals.appendLog(globals.logActivity(this.getClass().getSimpleName()));
    }
}
