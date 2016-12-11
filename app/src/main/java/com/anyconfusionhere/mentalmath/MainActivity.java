package com.anyconfusionhere.mentalmath;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    ImageView startButton, playAgainButton;
    TextView finalScore, currentProblem, currentAnswer, timeLeft, scoreTextView;
    RelativeLayout gameRelativeLayout;
    int score, questions;
    MediaPlayer correctMP, inCorrectMP;
    CountDownTimer timer;
    MathModel mathModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
        score = 0;
        questions = 0;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(questions));
        mathModel = new MathModel();
        currentProblem.setText(mathModel.newProblem());
    }


    public void start(View view) {
        startButton.setVisibility(View.INVISIBLE);
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


    public void check(View view) {
        Log.d("MYAPP", String.valueOf(currentAnswer.getText()));
        if (currentAnswer.getText().equals(mathModel.getAnswer())) {
            if(correctMP.isPlaying()) {
                correctMP.stop();
            }
            correctMP.start();
            score++;
        } else {
            inCorrectMP.start();
        }
        currentAnswer.setText("");
        questions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(questions));
        currentProblem.setText(mathModel.newProblem());

    }

    public void push(View view) {

        if (currentAnswer.getText().toString().length() < 6) {
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
}

