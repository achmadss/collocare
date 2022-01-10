package com.susiapp.collocare.Views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.susiapp.collocare.Adapters.QuizAdapter;
import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.Quiz;
import com.susiapp.collocare.R;

public class QuizActivity extends AppCompatActivity {

    public ListView listView;
    QuizAdapter quizAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listview_quiz);

        quizAdapter = new QuizAdapter(QuizActivity.this, listView);
        listView.setAdapter(quizAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Intent i = new Intent(QuizActivity.this, QuizTypeActivity.class);
                String name = "type";
                int type = 0;
                if(position == 0) {
                    type = Quiz.QUIZ_MULTIPLE_CHOICE;
                } else if(position == 1) {
                    type = Quiz.QUIZ_UNDERSCORE;
                } else if(position == 2) {
                    type = Quiz.QUIZ_FILL_IN_THE_BLANKS;
                }
                i.putExtra(name, type);
                startActivity(i);
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
