package com.anyconfusionhere.mentalmath;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    ImageView startButton;
    ImageView playAgainButton;
    TextView finalScore;
    TextView currentProblem;
    TextView currentAnswer;
    TextView timeLeft;
    TextView scoreTextView;
    RelativeLayout gameRelativeLayout;
    int answer;
    Random rand;
    int score;
    int questions;
    int operator;
    int a;
    int b;
    double d;
    String charVal;


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
        new CountDownTimer(60400, 1000) {

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
        }.start();
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


        operator = rand.nextInt(3);
        if (operator == 0) {
            a = rand.nextInt(21);
            b = rand.nextInt(21);
            answer = a + b;
            currentProblem.setText(Integer.toString(a) + "+" + Integer.toString(b) + " =");
        } else if (operator == 1) {
            a = rand.nextInt(21);
            b = rand.nextInt(21);
            answer = a - b;
            currentProblem.setText(Integer.toString(a) + "-" + Integer.toString(b) + " =");

        } else if (operator == 2) {
            a = rand.nextInt(12);
            b = rand.nextInt(12);
            answer = a * b;
            currentProblem.setText(Integer.toString(a) + "*" + Integer.toString(b) + " =");
//        } else if (operator == 3) {
//
//            do {
//
//                a = rand.nextInt(100) +2;
//                b = rand.nextInt(100) + 1;
//                answer = a / b;
//                charVal = String.valueOf(answer);
//            }while (isStringInt(charVal) == false);
//            currentProblem.setText(Integer.toString(a) + "/" + Integer.toString(b) + " =");
//        }


        }
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(questions));
        currentAnswer.setText("");
    }


    public void check(View view) {

        if (currentAnswer.getText().equals(String.valueOf(answer))) {
            Toast.makeText(getApplicationContext(), "CORRECT", Toast.LENGTH_SHORT).show();
            score++;
            questions++;
        } else {
            Toast.makeText(getApplicationContext(), "WRONG", Toast.LENGTH_SHORT).show();
            questions++;
        }

        newProblem();
    }

    public void push(View view) {
        if (currentAnswer.getText().toString().length() < 3) {

            currentAnswer.setText(currentAnswer.getText() + view.getTag().toString());

        }

    }

    public boolean isStringInt(String s) {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        operator = rand.nextInt(3);
        newProblem();
        score = 0;
        questions = 0;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(questions));
    }

}

