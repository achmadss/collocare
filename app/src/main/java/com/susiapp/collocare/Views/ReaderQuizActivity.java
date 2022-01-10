package com.susiapp.collocare.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.susiapp.collocare.Adapters.ReaderQuizAdapter;
import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.Quiz;
import com.susiapp.collocare.R;

public class ReaderQuizActivity extends AppCompatActivity {

    Globals globals = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        final ViewPager viewPager = findViewById(R.id.view_pager);

        ReaderQuizAdapter adapter = new ReaderQuizAdapter(ReaderQuizActivity.this);
        viewPager.setAdapter(adapter);

        final Toolbar top = findViewById(R.id.reader_toolbar_top);
        final Toolbar bottom = findViewById(R.id.reader_toolbar_bottom);
        TextView quizTitle = findViewById(R.id.reader_toolbar_chapter_title);
        TextView quizNumber = findViewById(R.id.reader_toolbar_chapter_number);

        final Quiz currentQuiz = globals.getCurrentQuiz();
        quizTitle.setText(currentQuiz.getQuizTitle());
        quizNumber.setText("Quiz "+currentQuiz.getQuizNumber());

        final TextView pageNumber = findViewById(R.id.pages);
        final int[] currentPage = new int[1];
        currentPage[0] = 1;
        final int pageCount = adapter.getCount()-1;


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //
            }

            @Override
            public void onPageSelected(int position) {
                if(!(position >= pageCount)) {
                    currentPage[0] = position+1;
                    pageNumber.setText(currentPage[0] +"/"+pageCount);
                    top.setVisibility(View.VISIBLE);
                    bottom.setVisibility(View.VISIBLE);
                } else {
                    top.setVisibility(View.GONE);
                    bottom.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //
            }
        });

        pageNumber.setText(currentPage[0] +"/"+pageCount);

    }


}
