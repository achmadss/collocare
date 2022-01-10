package com.susiapp.collocare.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.Quiz;
import com.susiapp.collocare.R;

import java.util.ArrayList;

public class QuizTypeAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private Globals globals = Globals.getInstance();
    private ArrayList<Quiz> quizzes;

    public QuizTypeAdapter(Context context, ListView listView, int type, SwipeRefreshLayout refreshLayout) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.listView = listView;
        this.refreshLayout = refreshLayout;
        this.quizzes = globals.getQuizBasedOnType(type);
    }

    @Override
    public int getCount() {
        return quizzes.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_quiz_type, parent, false);

        Quiz currentQuiz = quizzes.get(position);

        TextView quizTitle = convertView.findViewById(R.id.quiz_title);
        TextView quizNumber = convertView.findViewById(R.id.quiz_number);

        quizTitle.setText(currentQuiz.getQuizTitle());
        quizNumber.setText("Quiz number "+ currentQuiz.getQuizNumber());

        return convertView;
    }

    public void updateList() {
        this.notifyDataSetChanged();
        listView.setAdapter(this);
        refreshLayout.setRefreshing(false);
    }

}
