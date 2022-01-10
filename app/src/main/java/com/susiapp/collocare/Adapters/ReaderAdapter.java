package com.susiapp.collocare.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.graphics.PorterDuff;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.Chapter;
import com.susiapp.collocare.R;
import com.susiapp.collocare.Views.ReaderActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ReaderAdapter extends PagerAdapter {

    private Globals globals = Globals.getInstance();
    private Context context;
    private LayoutInflater inflater;
    private Chapter currentChapter = globals.getCurrentChapter();
    private ArrayList<String> images = currentChapter.getImageURLS();
    private String chapterNumber = currentChapter.getChapterNumber();
    private int chapterNumberInteger = Integer.parseInt(chapterNumber);
    private SharedPreferences prefs = null;

    public ReaderAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size()+1;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        prefs = context.getSharedPreferences("com.example.collocare", Context.MODE_PRIVATE);
        if(prefs.getBoolean("firstRead", true)) {
            // if first time reading
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setPositiveButton("Ok, got it", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    prefs.edit().putBoolean("firstRead", false).apply();
                }
            });
            final View viewDialog = inflater.inflate(R.layout.dialog_navigate, null);
            TextView dialogTitle = viewDialog.findViewById(R.id.dialog_title);
            TextView dialogDesc = viewDialog.findViewById(R.id.dialog_desc);
            ImageView dialogImage = viewDialog.findViewById(R.id.dialog_image);

            dialogTitle.setText("Navigate through the chapter");
            dialogDesc.setText("Navigate by swiping left or right" +
                    "\nSwipe left to go to the next page\nSwipe right to go to the previous page");
            dialogDesc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dialogImage.setImageResource(R.drawable.gesture_transparent);

            builder.setView(viewDialog);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            prefs.edit().putBoolean("firstRead", false).apply();
        }

        int res;

        if(position != images.size()) {
            res = R.layout.reader_slide;
        } else {
            res = R.layout.reader_slide_last;
        }

        View view = inflater.inflate(res,container,false);

        if(position != images.size()) {
            final RelativeLayout layout_slide = view.findViewById(R.id.slidelayout);
            final ProgressBar pb;
            final ImageView image_slide;
            image_slide = (ImageView) view.findViewById(R.id.slideimg);
            image_slide.setVisibility(View.INVISIBLE);
            layout_slide.setBackgroundColor(Color.rgb(0, 0, 0));
            pb = view.findViewById(R.id.progressBar);
            pb.getIndeterminateDrawable()
                    .setColorFilter(
                            view.getResources().getColor(R.color.lightBlue),
                            PorterDuff.Mode.SRC_IN);
            Picasso.get().load(images.get(position)).into(image_slide, new Callback() {
                @Override
                public void onSuccess() {
                    image_slide.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } else {
            TextView end = view.findViewById(R.id.end_chapter);
            final Button next = view.findViewById(R.id.next);
            Button close = view.findViewById(R.id.close);
            if(Integer.parseInt(chapterNumber) < globals.getNumberOfChapters()) {
                end.setText(end.getText().toString()+chapterNumber);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, ReaderActivity.class);
                        globals.setCurrentChapter(globals.getChapters().get(chapterNumberInteger));
                        context.startActivity(i);
                        ((Activity)context).finish();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Activity)context).finish();
                    }
                });
            } else if (Integer.parseInt(chapterNumber) == globals.getNumberOfChapters()){
                close.setVisibility(View.INVISIBLE);
                close.setEnabled(false);
                end.setText("Last chapter reached!");
                next.setText(" Close Viewer");
                next.setCompoundDrawablePadding(0);
                next.setPadding(
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources().getDisplayMetrics()),
                        0,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources().getDisplayMetrics()),
                        0);
                next.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close_white_24dp, 0, 0, 0);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Activity)context).finish();
                    }
                });
            }

        }

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (RelativeLayout) o);
    }

}
