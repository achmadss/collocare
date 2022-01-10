package com.susiapp.collocare.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.susiapp.collocare.Async.GetStory;
import com.susiapp.collocare.Adapters.StoryAdapter;
import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.Chapter;
import com.susiapp.collocare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class StoryActivity extends AppCompatActivity {

    public ListView listView;
    SwipeRefreshLayout refreshLayout;
    StoryAdapter storyAdapter;
    private Globals globals = Globals.getInstance();
//    CarouselView carouselView;
//    int[] sampleImages = {
//            R.drawable.gesture_transparent,
//            R.drawable.gesture_transparent,
//            R.drawable.gesture_transparent,
//            R.drawable.gesture_transparent,
//            R.drawable.gesture_transparent
//    };


    @Override
    protected void onResume() {
        super.onResume();
        globals.appendLog(globals.logActivity(this.getClass().getSimpleName()));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listview_chapter_list);
        refreshLayout = findViewById(R.id.refresh_layout);

        storyAdapter = new StoryAdapter(StoryActivity.this, listView, refreshLayout);
        listView.setAdapter(storyAdapter);

//        final FrameLayout frameLayout = findViewById(R.id.intro);

//        carouselView = (CarouselView) findViewById(R.id.carouselView);
//        carouselView.setPageCount(sampleImages.length);
//        carouselView.setImageClickListener(new ImageClickListener() {
//            @Override
//            public void onClick(int position) {
//                if(position == sampleImages.length-1) {
//                    frameLayout.setVisibility(View.GONE);
//                }
//            }
//        });
//        carouselView.setImageListener(imageListener);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String res = null;
                        try {
                            res = new GetStory().execute().get();
                            if(res.equals("success")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        storyAdapter.updateList();
                                        Toast.makeText(
                                                StoryActivity.this,
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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Intent i = new Intent(StoryActivity.this, ReaderActivity.class);
                Chapter current = globals.getChapters().get(position);
                globals.setCurrentChapter(current);

                SharedPreferences prefs = getSharedPreferences("com.example.collocare", MODE_PRIVATE);
                prefs.edit().putString("lastChapterReadTitle", current.getChapterTitle()).apply();
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM y H:m:s Z", Locale.getDefault());
                String df = format.format(date);
                prefs.edit().putString("dateLastChapterRead", df).apply();
                int count = prefs.getInt("chapterReadCount", 0);
                count = count+1;
                prefs.edit().putInt("chapterReadCount", count).apply();

                globals.appendLog(
                        globals.logReadChapter(
                                df,
                                current.getChapterTitle(),
                                current.getChapterNumber()
                        )
                );

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

    //    ImageListener imageListener = new ImageListener() {
//        @Override
//        public void setImageForPosition(int position, ImageView imageView) {
//            imageView.setImageResource(sampleImages[position]);
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        }
//    };

}
