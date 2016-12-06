package com.anyconfusionhere.mentalmath;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.view.Window;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;


public class MainActivity extends AppCompatActivity {
    ImageView startButton, playAgainButton;
    TextView finalScore, currentProblem, currentAnswer, timeLeft, scoreTextView;
    RelativeLayout gameRelativeLayout;
    int answer, score, questions, operator, a, b;
    MediaPlayer correctMP, inCorrectMP;
    Random rand;
    CountDownTimer timer;
    HashMap<Integer,Integer> exponentMap;

    public void start(View view) {

        gameRelativeLayout.setVisibility(View.VISIBLE);
        currentAnswer.setVisibility(View.VISIBLE);
        currentProblem.setVisibility(View.VISIBLE);
        timeLeft.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        finalScore.setVisibility(View.INVISIBLE);
        score = 0;
        questions = 0;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(questions));
        this.newTimer();
        timer.start();
    }

    public void newTimer() {


        timer = new CountDownTimer(60400, 1000) {

            public void onTick(long millisecondsUntilDone) {

                timeLeft.setText(String.valueOf(millisecondsUntilDone / 1000) + "s");
            }

            public void onFinish() {
                timeLeft.setText("0s");
                Toast.makeText(getApplicationContext(), "TIME HAS RUN OUT", Toast.LENGTH_LONG).show();
                gameRelativeLayout.setVisibility(View.INVISIBLE);
                currentAnswer.setVisibility(View.INVISIBLE);
                currentProblem.setVisibility(View.INVISIBLE);
                timeLeft.setVisibility(View.INVISIBLE);
                scoreTextView.setVisibility(View.INVISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
                finalScore.setText("YOUR FINAL SCORE IS: " + scoreTextView.getText().toString());
                finalScore.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.INVISIBLE);
            }
        };
    }


    public String slice_end(String s, int endIndex) {
        if (endIndex < 0) endIndex = s.length() + endIndex;
        return s.substring(0, endIndex);
    }

    public void pull(View view) {
        if (currentAnswer.length() >0) {
            String newCurrentAnswer = slice_end(currentAnswer.getText().toString(), currentAnswer.getText().toString().length() - 1);
            currentAnswer.setText(newCurrentAnswer);
        }
    }

    public void newProblem() {


        operator = rand.nextInt(23);
//        operator = 1000;
        if (operator <= 4) {
            a = rand.nextInt(21) + 1;
            b = rand.nextInt(21) + 1;
            answer = a + b;
            currentProblem.setText(Integer.toString(a) + "+" + Integer.toString(b) + " =");
        } else if (operator > 4 && operator <= 8) {
            a = rand.nextInt(21) + 1;
            b = rand.nextInt(21) + 1;
            answer = a - b;
            currentProblem.setText(Integer.toString(a) + "-" + Integer.toString(b) + " =");

        } else if (operator > 8 && operator <= 12) {
            a = rand.nextInt(12) + 1;
            b = rand.nextInt(12) + 1;
            answer = a * b;
            currentProblem.setText(Integer.toString(a) + "*" + Integer.toString(b) + " =");
        } else if (operator > 12 && operator <= 16) {
            answer = rand.nextInt(12) + 1;
            b = rand.nextInt(12) + 1;
            a = answer * b;
            currentProblem.setText(Integer.toString(a) + "/" + Integer.toString(b) + " =");
        } else if (operator > 16 && operator <= 19) {
            a = rand.nextInt(10) + 3;
            b = rand.nextInt(exponentMap.get(a)) + 2;
            answer = ((int) Math.pow((double) a, (double) b));
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(String.valueOf(a) + String.valueOf(b) + " =" );
            if (stringBuilder.length() == 4) {
                stringBuilder.setSpan(new SuperscriptSpan(), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                stringBuilder.setSpan(new RelativeSizeSpan(0.75f), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (stringBuilder.length() == 5) {
                if (a - 10 >= 0) {
                    stringBuilder.setSpan(new SuperscriptSpan(), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    stringBuilder.setSpan(new RelativeSizeSpan(0.75f), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    stringBuilder.setSpan(new SuperscriptSpan(), 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    stringBuilder.setSpan(new RelativeSizeSpan(0.75f), 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            currentProblem.setText(stringBuilder);

        } else if (operator > 19 && operator <= 22) {
            answer = rand.nextInt(10) + 3;
            b = rand.nextInt(exponentMap.get(answer)) + 2;
            a = (int) Math.pow((double)answer, (double)b);
            if(b == 2) {
                currentProblem.setText("\u221A" + String.valueOf(a));
            } else {
                SpannableStringBuilder stringBuilder = new SpannableStringBuilder(String.valueOf(b) + "\u221A" + String.valueOf(a));
                stringBuilder.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                stringBuilder.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                currentProblem.setText(stringBuilder);
            }
        }
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(questions));
        currentAnswer.setText("");
    }


    public void check(View view) {

        if (currentAnswer.getText().equals(String.valueOf(answer))) {

            correctMP.start();
            score++;
        } else {
            inCorrectMP.start();
        }
        questions++;
        newProblem();
    }

    public void push(View view) {
        if (currentAnswer.getText().toString().length() < 3) {

            currentAnswer.setText(currentAnswer.getText() + view.getTag().toString());

        }

    }

    @Override
    public void onBackPressed() {

        gameRelativeLayout.setVisibility(View.INVISIBLE);
        currentAnswer.setVisibility(View.INVISIBLE);
        currentProblem.setVisibility(View.INVISIBLE);
        timeLeft.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.VISIBLE);
        finalScore.setText("YOUR FINAL SCORE IS: " + scoreTextView.getText().toString());
        finalScore.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        this.timer.cancel();
        this.newTimer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        exponentMap = new HashMap<Integer, Integer>();
        exponentMap.put(2,5);
        exponentMap.put(3,3);
        exponentMap.put(4,2);
        exponentMap.put(5,2);
        exponentMap.put(6,2);
        exponentMap.put(7,1);
        exponentMap.put(8,1);
        exponentMap.put(9,1);
        exponentMap.put(10,2);
        exponentMap.put(11,1);
        exponentMap.put(12,1);
        exponentMap.put(13,1);
        correctMP = MediaPlayer.create(this, R.raw.correct);
        inCorrectMP = MediaPlayer.create(this, R.raw.incorrect);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (ImageView) findViewById(R.id.startButton);
        playAgainButton = (ImageView) findViewById(R.id.playAgain);
        currentProblem = (TextView) findViewById(R.id.currentProblem);
        currentAnswer = (TextView) findViewById(R.id.currentAnswer);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        finalScore = (TextView) findViewById(R.id.finalScore);
        timeLeft = (TextView) findViewById(R.id.timeLeft);
        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);
        rand = new Random();
        operator = rand.nextInt(5);
        newProblem();
        score = 0;
        questions = 0;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(questions));
    }

}

