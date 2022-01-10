package com.susiapp.collocare.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.Quiz;
import com.susiapp.collocare.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


public class ReaderQuizAdapter extends PagerAdapter implements View.OnDragListener, View.OnLongClickListener {

    private Globals globals = Globals.getInstance();
    private Quiz currentQuiz = globals.getCurrentQuiz();
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> images = currentQuiz.getImageURLS();
    private String quizType = currentQuiz.getQuizType();
    private String imageQuizURL = currentQuiz.getImageQuiz();
    private int quizTypeConst;
//    private SharedPreferences prefs = null;
    private boolean isAnswerCorrect;

    private View viewQuiz;
    private TextView currentOption;

    public ReaderQuizAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
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
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int res;

        if(quizType.equals("1")) {
            quizTypeConst = Quiz.QUIZ_MULTIPLE_CHOICE;
        } else if(quizType.equals("2")) {
            quizTypeConst = Quiz.QUIZ_UNDERSCORE;
        } else {
            quizTypeConst = Quiz.QUIZ_FILL_IN_THE_BLANKS;
        }

        if(position != images.size()) {
            res = R.layout.reader_slide;
        } else {
            if(quizTypeConst == Quiz.QUIZ_MULTIPLE_CHOICE) {
                res = R.layout.quiz_level_1;
            } else if(quizTypeConst == Quiz.QUIZ_UNDERSCORE) {
                res = R.layout.quiz_level_2;
            } else {
                res = R.layout.quiz_level_3;
            }
        }
        ArrayList<TextView> characters = new ArrayList<>();
        viewQuiz = inflater.inflate(res,container,false);
        if(position != images.size()) {
//            Toast.makeText(context, "TES", Toast.LENGTH_SHORT).show();
            //QUIZ STORY
            final RelativeLayout layout_slide = viewQuiz.findViewById(R.id.slidelayout);
            final ProgressBar pb;
            final ImageView image_slide;
            image_slide = (ImageView) viewQuiz.findViewById(R.id.slideimg);
            image_slide.setVisibility(View.INVISIBLE);
            layout_slide.setBackgroundColor(Color.rgb(0, 0, 0));
            pb = viewQuiz.findViewById(R.id.progressBar);
            pb.getIndeterminateDrawable()
                    .setColorFilter(
                            viewQuiz.getResources().getColor(R.color.lightBlue),
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
            ProgressBar pbQuiz = null;
            ImageView imageQuiz = null;

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setCancelable(false);
//            builder.setPositiveButton("Ok, got it", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //
//                }
//            });
            final View viewDialog = inflater.inflate(R.layout.dialog_quiz_result, null);
            builder.setView(viewDialog);
            final AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Close quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ((Activity) context).finish();
                }
            });
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button positive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button negative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    positive.setAllCaps(false);
                    negative.setAllCaps(false);
                    positive.setTextColor(viewQuiz.getResources().getColor(R.color.white));
                    negative.setTextColor(viewQuiz.getResources().getColor(R.color.white));

                    TextView answerResult = alertDialog.getWindow().findViewById(R.id.quiz_text_result);
                    Button close = alertDialog.getWindow().findViewById(R.id.btn_dialog_close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).performClick();
                        }
                    });
                    if(isAnswerCorrect) {
                        answerResult.setText("Correct!!!");
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.quiz_result_correct);
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
                    } else {
                        answerResult.setText("Incorrect...");
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.quiz_result_incorrect);
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.VISIBLE);
                    }
                }
            });

            if(quizTypeConst == Quiz.QUIZ_MULTIPLE_CHOICE) {
                isAnswerCorrect = false;
                pbQuiz = viewQuiz.findViewById(R.id.imageQuizLoading1);
                imageQuiz = viewQuiz.findViewById(R.id.imageQuiz1);
                loadImage(imageQuiz, imageQuizURL, pbQuiz);
                // get options text view
                TextView option1 = viewQuiz.findViewById(R.id.quiz_option1);
                TextView option2 = viewQuiz.findViewById(R.id.quiz_option2);
                TextView option3 = viewQuiz.findViewById(R.id.quiz_option3);
                // get options
                option1.setText(currentQuiz.getOptionsQuiz().get(0));
                option2.setText(currentQuiz.getOptionsQuiz().get(1));
                option3.setText(currentQuiz.getOptionsQuiz().get(2));
                // set tag
                option1.setTag("Option 1");
                option2.setTag("Option 2");
                option3.setTag("Option 3");
                // on long click
                option1.setOnLongClickListener(this);
                option2.setOnLongClickListener(this);
                option3.setOnLongClickListener(this);
                // on drag
                viewQuiz.findViewById(R.id.quiz_layout_options).setOnDragListener(this);
                viewQuiz.findViewById(R.id.quiz_layout_answer).setOnDragListener(this);

                final Button buttonAnswer = viewQuiz.findViewById(R.id.quiz_answer_button_lv1);
                final TextView quizAnswer = viewQuiz.findViewById(R.id.quiz_answer_lv1);
                buttonAnswer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isAnswerCorrect = quizAnswer.getText().toString().equals(currentQuiz.getAnswerQuiz());
                        updateSummaryQuizData(isAnswerCorrect);
                        alertDialog.show();
                    }
                });



            } else if(quizTypeConst == Quiz.QUIZ_UNDERSCORE) {
                isAnswerCorrect = false;
                pbQuiz = viewQuiz.findViewById(R.id.imageQuizLoading2);
                imageQuiz = viewQuiz.findViewById(R.id.imageQuiz2);
                loadImage(imageQuiz, imageQuizURL, pbQuiz);

                String answer = currentQuiz.getAnswerQuiz();
                String[] answerSeparated = answer.split("");

                int random = new Random().nextInt(answerSeparated.length)+1;

                TextView text = new TextView(context);
                StringBuilder add = new StringBuilder();
                for (int i = 0; i < answerSeparated.length; i++) {
                    if(i == 0) continue;
                    if(answerSeparated[i].equals(" ")) {
                        add.append("  ");
                    } else {
                        add.append("_ ");
                    }
                    // for randomizing if needed
//                    if(i == random) {
//                        add.append(answerSeparated[i]);
//                        add.append(" ");
//                    } else {
//                        if(answerSeparated[i].equals(" ")) {
//                            add.append("  ");
//                        } else {
//                            add.append("_ ");
//                        }
//                    }
                }
                text.setText(add.toString());

                text.setId(View.generateViewId());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.addRule(RelativeLayout.CENTER_IN_PARENT, text.getId());
                text.setLayoutParams(params);
                text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                text.setTextSize((int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 12, viewQuiz.getResources()
                                .getDisplayMetrics()));
                text.setTextColor(ColorStateList.valueOf(viewQuiz.getResources().getColor(R.color.colorAccent)));
                characters.add(text);

                ViewGroup parent = viewQuiz.findViewById(R.id.quiz_layout_underscore);
                if(quizTypeConst == Quiz.QUIZ_UNDERSCORE) {
                    for (int i = 0; i < characters.size(); i++) {
                        parent.addView(characters.get(i));
                    }
                }

                final EditText answerUnderscore = viewQuiz.findViewById(R.id.quiz_answer_lv2);
                answerUnderscore.setFilters(new InputFilter[]{new InputFilter.LengthFilter(answerSeparated.length-1)});

                final Button buttonAnswer2 = viewQuiz.findViewById(R.id.quiz_answer_button_lv2);
                buttonAnswer2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isAnswerCorrect = answerUnderscore.getText().toString().equals(currentQuiz.getAnswerQuiz());
                        updateSummaryQuizData(isAnswerCorrect);
                        alertDialog.show();

                    }
                });

            } else if(quizTypeConst == Quiz.QUIZ_FILL_IN_THE_BLANKS) {
                isAnswerCorrect = false;
                pbQuiz = viewQuiz.findViewById(R.id.imageQuizLoading3);
                imageQuiz = viewQuiz.findViewById(R.id.imageQuiz3);
                loadImage(imageQuiz, imageQuizURL, pbQuiz);
                final EditText answerFITB = viewQuiz.findViewById(R.id.quiz_answer_lv3);
                answerFITB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(currentQuiz.getAnswerQuiz().length())});

                final Button buttonAnswer3 = viewQuiz.findViewById(R.id.quiz_answer_button_lv3);
                buttonAnswer3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isAnswerCorrect = answerFITB.getText().toString().equals(currentQuiz.getAnswerQuiz());
                        updateSummaryQuizData(isAnswerCorrect);
                        alertDialog.show();
                    }
                });
            }
        }

        container.addView(viewQuiz);

        return viewQuiz;

    }

    private void updateSummaryQuizData(boolean answerCorrect) {
        SharedPreferences prefs = context.getSharedPreferences("com.example.collocare", MODE_PRIVATE);
        prefs.edit().putString("lastQuizTakenTitle", currentQuiz.getQuizTitle()).apply();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM y H:m:s Z", Locale.getDefault());
        String df = format.format(date);
        prefs.edit().putString("dateLastQuizTaken", df).apply();
        int count = prefs.getInt("quizTakenTotalCount", 0);
        int countNew = count + 1;
        prefs.edit().putInt("quizTakenTotalCount", countNew).apply();

        if(currentQuiz.getQuizTypeConst() == Quiz.QUIZ_MULTIPLE_CHOICE) {
            int countType = prefs.getInt("quizTakenTypeOneCount", 0);
            int countTypeNew = countType + 1;
            prefs.edit().putInt("quizTakenTypeOneCount", countTypeNew).apply();
            if(answerCorrect) {
                int countTypeCorrect = prefs.getInt("quizTypeOneCorrect", 0);
                int countTypeCorrectNew = countTypeCorrect + 1;
                prefs.edit().putInt("quizTypeOneCorrect", countTypeCorrectNew).apply();
            } else {
                int countTypeIncorrect = prefs.getInt("quizTypeOneIncorrect", 0);
                int countTypeIncorrectNew = countTypeIncorrect + 1;
                prefs.edit().putInt("quizTypeOneIncorrect", countTypeIncorrectNew).apply();
            }
        } else if(currentQuiz.getQuizTypeConst() == Quiz.QUIZ_UNDERSCORE) {
            int countType = prefs.getInt("quizTakenTypeTwoCount", 0);
            int countTypeNew = countType + 1;
            prefs.edit().putInt("quizTakenTypeTwoCount", countTypeNew).apply();
            if(answerCorrect) {
                int countTypeCorrect = prefs.getInt("quizTypeTwoCorrect", 0);
                int countTypeCorrectNew = countTypeCorrect + 1;
                prefs.edit().putInt("quizTypeTwoCorrect", countTypeCorrectNew).apply();
            } else {
                int countTypeIncorrect = prefs.getInt("quizTypeTwoIncorrect", 0);
                int countTypeIncorrectNew = countTypeIncorrect + 1;
                prefs.edit().putInt("quizTypeTwoIncorrect", countTypeIncorrectNew).apply();
            }
        } else if(currentQuiz.getQuizTypeConst() == Quiz.QUIZ_FILL_IN_THE_BLANKS) {
            int countType = prefs.getInt("quizTakenTypeThreeCount", 0);
            int countTypeNew = countType + 1;
            prefs.edit().putInt("quizTakenTypeThreeCount", countTypeNew).apply();
            if(answerCorrect) {
                int countTypeCorrect = prefs.getInt("quizTypeThreeCorrect", 0);
                int countTypeCorrectNew = countTypeCorrect + 1;
                prefs.edit().putInt("quizTypeThreeCorrect", countTypeCorrectNew).apply();
            } else {
                int countTypeIncorrect = prefs.getInt("quizTypeThreeIncorrect", 0);
                int countTypeIncorrectNew = countTypeIncorrect + 1;
                prefs.edit().putInt("quizTypeThreeIncorrect", countTypeIncorrectNew).apply();
            }
        }

        globals.appendLog(
                globals.logQuizAnswer(
                        df,
                        currentQuiz.getQuizTitle(),
                        currentQuiz.getQuizNumber(),
                        currentQuiz.getQuizType(),
                        isAnswerCorrect,
                        countNew
                )
        );

    }

    private void loadImage(final ImageView into, String url, final ProgressBar loading) {
        loading.getIndeterminateDrawable()
                .setColorFilter(
                        viewQuiz.getResources().getColor(R.color.lightBlue),
                        PorterDuff.Mode.SRC_IN);

        Picasso.get().load(url).into(into, new Callback() {
            @Override
            public void onSuccess() {
                into.setVisibility(View.VISIBLE);
                loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public boolean onLongClick(View view) {
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

        // Instantiates the drag shadow builder.
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        // Starts the drag
        view.startDrag(data//data to be dragged
                , shadowBuilder //drag shadow
                , view//local data about the drag and drop operation
                , 0//no needed flags
        );

        //Set view visibility to INVISIBLE as we are going to drag the view
        view.setVisibility(View.INVISIBLE);
        return true;
    }

    @Override
    public boolean onDrag(View view, DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();
        View v = (View) event.getLocalState();
        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // returns true to indicate that the View can accept the dragged data.
                    return true;
                }
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                // on drag exit
                return true;
            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);
                // Gets the text data from the item.
                String dragData = item.getText().toString();

                TextView opt = (TextView) v;
                TextView ans = viewQuiz.findViewById(R.id.quiz_answer_lv1);
                ans.setText(opt.getText());
                ans.setTextColor(ColorStateList.valueOf(viewQuiz.getResources().getColor(R.color.white)));
                if(currentOption == null) {
                    v.setVisibility(View.GONE);
                    currentOption = (TextView) v;
                } else {
                    v.setVisibility(View.GONE);
                    currentOption.setVisibility(View.VISIBLE);
                    currentOption = (TextView) v;
                }
                // Returns true. DragEvent.getResult() will return true.
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                // Does a getResult(), and displays what happened.
                if (event.getResult()) {
//                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
                    v.setVisibility(View.VISIBLE);
                }
                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.


            default:
//                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (RelativeLayout) object);
    }
}
