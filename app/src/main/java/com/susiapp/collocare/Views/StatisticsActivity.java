package com.susiapp.collocare.Views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.susiapp.collocare.Async.UpdateToServer;
import com.susiapp.collocare.Async.UploadLog;
import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.User;
import com.susiapp.collocare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class StatisticsActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    Globals globals = Globals.getInstance();

    @Override
    protected void onResume() {
        super.onResume();
        globals.appendLog(globals.logActivity(this.getClass().getSimpleName()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        prefs = getSharedPreferences("com.example.collocare", MODE_PRIVATE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        User user = new User(prefs);
        // story
        TextView lastChapterReadTitle = findViewById(R.id.statistic_last_read);
        TextView dateLastChapterRead = findViewById(R.id.statistic_last_read_date);
        TextView chapterReadCount = findViewById(R.id.statistic_read_count);
        lastChapterReadTitle.setText(user.getLastChapterReadTitle());
        dateLastChapterRead.setText(user.getDateLastChapterRead());
        chapterReadCount.setText(Integer.toString(user.getChapterReadCount()) + " time(s)");

        // quiz overall
        TextView lastQuizTakenTitle = findViewById(R.id.statistic_last_quiz);
        TextView dateLastQuizTaken = findViewById(R.id.statistic_last_quiz_date);
        TextView quizTakenTotalCount = findViewById(R.id.statistic_quiz_count);
        lastQuizTakenTitle.setText(user.getLastQuizTakenTitle());
        dateLastQuizTaken.setText(user.getDateLastQuizTaken());
        quizTakenTotalCount.setText(user.getQuizTakenTotalCount() + " time(s)");

        // quiz based on type
        TextView quizTakenTypeOneCount = findViewById(R.id.statistic_level1_count);
        TextView quizTakenTypeTwoCount = findViewById(R.id.statistic_level2_count);
        TextView quizTakenTypeThreeCount = findViewById(R.id.statistic_level3_count);
        quizTakenTypeOneCount.setText(Integer.toString(user.getQuizTakenTypeOneCount()));
        quizTakenTypeTwoCount.setText(Integer.toString(user.getQuizTakenTypeTwoCount()));
        quizTakenTypeThreeCount.setText(Integer.toString(user.getQuizTakenTypeThreeCount()));

        // correct answer
        TextView quizTypeOneCorrect = findViewById(R.id.statistic_level1_count_correct);
        TextView quizTypeTwoCorrect = findViewById(R.id.statistic_level2_count_correct);
        TextView quizTypeThreeCorrect = findViewById(R.id.statistic_level3_count_correct);
        quizTypeOneCorrect.setText(Integer.toString(user.getQuizTypeOneCorrect()));
        quizTypeTwoCorrect.setText(Integer.toString(user.getQuizTypeTwoCorrect()));
        quizTypeThreeCorrect.setText(Integer.toString(user.getQuizTypeThreeCorrect()));

        // incorrect answer
        TextView quizTypeOneIncorrect = findViewById(R.id.statistic_level1_count_incorrect);
        TextView quizTypeTwoIncorrect = findViewById(R.id.statistic_level2_count_incorrect);
        TextView quizTypeThreeIncorrect = findViewById(R.id.statistic_level3_count_incorrect);
        quizTypeOneIncorrect.setText(Integer.toString(user.getQuizTypeOneIncorrect()));
        quizTypeTwoIncorrect.setText(Integer.toString(user.getQuizTypeTwoIncorrect()));
        quizTypeThreeIncorrect.setText(Integer.toString(user.getQuizTypeThreeIncorrect()));

        // upload to server
        Button uploadBtn = findViewById(R.id.upload_btn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM y H:m:s Z", Locale.getDefault());
                    String df = format.format(date);
                    prefs.edit().putString("timeUpdated", df).apply();
                    String result = new UpdateToServer(prefs.getString("username", ""), prefs).execute().get();
                    String logResult = new UploadLog(prefs.getString("username","")).execute().get();
                    if(result.equals("success")) {
                        Toast.makeText(StatisticsActivity.this, "Upload success\n"+logResult, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StatisticsActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}