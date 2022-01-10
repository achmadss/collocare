package com.susiapp.collocare.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.R;

public class QuizAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ListView listView;

    public QuizAdapter(
            Context context,
            ListView listView) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return 3;
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
        convertView = inflater.inflate(R.layout.row_quiz, parent, false);

        if(position == 0) {
            TextView quizTypeText = convertView.findViewById(R.id.quiz_type);
            TextView quizTypeDesc = convertView.findViewById(R.id.quiz_type_desc);
            quizTypeText.setText("Level 1");
            quizTypeDesc.setText("Multiple Choice");
        } else if(position == 1) {
            TextView quizTypeText = convertView.findViewById(R.id.quiz_type);
            TextView quizTypeDesc = convertView.findViewById(R.id.quiz_type_desc);
            quizTypeText.setText("Level 2");
            quizTypeDesc.setText("Fill in the blank (with hints)");
        } else if(position == 2) {
            TextView quizTypeText = convertView.findViewById(R.id.quiz_type);
            TextView quizTypeDesc = convertView.findViewById(R.id.quiz_type_desc);
            quizTypeText.setText("Level 3");
            quizTypeDesc.setText("Fill in the blank (no hints)");
        }

        return convertView;
    }

}
