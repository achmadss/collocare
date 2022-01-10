package com.susiapp.collocare.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.Chapter;
import com.susiapp.collocare.R;

import java.util.ArrayList;

public class StoryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private Globals globals = Globals.getInstance();
    private ArrayList<Chapter> chapters = globals.getChapters();

    public StoryAdapter(Context context, ListView listView, SwipeRefreshLayout refreshLayout) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listView = listView;
        this.refreshLayout = refreshLayout;
    }


    @Override
    public int getCount() {
        return chapters.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_story, parent, false);

        Chapter currentChapter = chapters.get(position);

        TextView chapterTitle = convertView.findViewById(R.id.chapter_title);
        TextView chapterNumber = convertView.findViewById(R.id.chapter_number);

        chapterTitle.setText(currentChapter.getChapterTitle());
        chapterNumber.setText("Chapter "+currentChapter.getChapterNumber());

        return convertView;

    }

    public void updateList() {
        this.notifyDataSetChanged();
        listView.setAdapter(this);
        refreshLayout.setRefreshing(false);
    }

}
