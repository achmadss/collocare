package com.susiapp.collocare.Views;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.susiapp.collocare.Adapters.ReaderAdapter;
import com.susiapp.collocare.Models.Chapter;
import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.R;

public class ReaderActivity extends AppCompatActivity {

    Globals globals = Globals.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ReaderAdapter adapter = new ReaderAdapter(ReaderActivity.this);
        viewPager.setAdapter(adapter);

        final Toolbar top = findViewById(R.id.reader_toolbar_top);
        final Toolbar bottom = findViewById(R.id.reader_toolbar_bottom);
        TextView chapterTitle = findViewById(R.id.reader_toolbar_chapter_title);
        TextView chapterNumber = findViewById(R.id.reader_toolbar_chapter_number);

        setSupportActionBar(top);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Chapter currentChapter = globals.getCurrentChapter();
        chapterTitle.setText(currentChapter.getChapterTitle());
        chapterNumber.setText("Chapter "+currentChapter.getChapterNumber());

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

        boolean topVisible = true;


        pageNumber.setText(currentPage[0] +"/"+pageCount);




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
