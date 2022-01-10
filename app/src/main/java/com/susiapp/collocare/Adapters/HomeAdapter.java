package com.susiapp.collocare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.susiapp.collocare.Views.QuizActivity;
import com.susiapp.collocare.R;
import com.susiapp.collocare.Views.StoryActivity;
import com.susiapp.collocare.Views.StatisticsActivity;

public class HomeAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    int menuCount = 3;

    public HomeAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);;
        this.context = context;
    }

    @Override
    public int getCount() {
        return menuCount;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        switch (position) {
            case 0: {
                convertView = inflater.inflate(R.layout.row_home, parent, false);
                TextView topText = convertView.findViewById(R.id.home_item_top_text);
                TextView itemName = convertView.findViewById(R.id.home_item_name);
                TextView itemDesc = convertView.findViewById(R.id.home_item_desc);
                CardView card = convertView.findViewById(R.id.home_item_card);
                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, StoryActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
                ImageView img = convertView.findViewById(R.id.img_home);
                img.setImageResource(R.drawable.home_story);
                topText.setText("Learn collocations through the story");
                itemName.setText("STORY");
                itemDesc.setText("Check out all the chapters!");
                break;
            }
            case 1: {
                convertView = inflater.inflate(R.layout.row_home, parent, false);
                TextView topText = convertView.findViewById(R.id.home_item_top_text);
                TextView itemName = convertView.findViewById(R.id.home_item_name);
                TextView itemDesc = convertView.findViewById(R.id.home_item_desc);
                CardView card = convertView.findViewById(R.id.home_item_card);
                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, QuizActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
                ImageView img = convertView.findViewById(R.id.img_home);
                img.setImageResource(R.drawable.home_quiz_2);
                topText.setText("Let's test what you learned");
                itemName.setText("QUIZ");
                itemDesc.setText("Check out all the quiz available!");
                break;
            }
            case 2: {
                convertView = inflater.inflate(R.layout.row_home, parent, false);
                TextView topText = convertView.findViewById(R.id.home_item_top_text);
                TextView itemName = convertView.findViewById(R.id.home_item_name);
                TextView itemDesc = convertView.findViewById(R.id.home_item_desc);
                CardView card = convertView.findViewById(R.id.home_item_card);
                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, StatisticsActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
                ImageView img = convertView.findViewById(R.id.img_home);
                img.setImageResource(R.drawable.home_progress);
                topText.setText("Your statistics on learning collocations");
                itemName.setText("STATISTICS");
                itemDesc.setText("Check out your statistics!");
                break;
            }
        }

        return  convertView;
    }
}

