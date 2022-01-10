package com.susiapp.collocare.Views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.susiapp.collocare.Adapters.QuizTypeAdapter;
import com.susiapp.collocare.Async.GetQuiz;
import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.Quiz;
import com.susiapp.collocare.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class QuizTypeActivity extends AppCompatActivity {

    ListView listView;
    QuizTypeAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    Globals globals = Globals.getInstance();
    ArrayList<Quiz> quizzes = new ArrayList<>();
    // onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_type);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        final int type = i.getIntExtra("type", 0);
        refreshLayout = findViewById(R.id.refresh_layout);
        listView = findViewById(R.id.listview_quiz_type);
        adapter = new QuizTypeAdapter(this, listView, type, refreshLayout);
        listView.setAdapter(adapter);

        TextView textType = findViewById(R.id.quiz_type_text);
        switch (type) {
            case Quiz.QUIZ_MULTIPLE_CHOICE: {
                textType.setText("Multiple Choice");
                quizzes.addAll(globals.getQuizTypeOne());
                break;
            }
            case Quiz.QUIZ_UNDERSCORE:{
                textType.setText("Fill in the blanks (with hints)");
                quizzes.addAll(globals.getQuizTypeTwo());
                break;
            }
            case Quiz.QUIZ_FILL_IN_THE_BLANKS: {
                textType.setText("Fill in the blanks (no hints)");
                quizzes.addAll(globals.getQuizTypeThree());
                break;
            }
            default:
                break;
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String res = null;
                        try {
                            res = new GetQuiz().execute().get();
                            if(res.equals("success")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.updateList();
                                        Toast.makeText(
                                                QuizTypeActivity.this,
                                                "refreshed",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(QuizTypeActivity.this, ReaderQuizActivity.class);
                Quiz currentQuiz = quizzes.get(position);
                globals.setCurrentQuiz(currentQuiz);
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