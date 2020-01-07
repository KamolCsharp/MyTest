package com.example.mytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TestActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    private static final long TEST_VAQTI = 30000;
    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_TEST_SONI = "keyTestSoni";
    private static final String KEY_VAQT = "keyVaqti";
    private static final String KEY_ANSWERD = "keyAnswered";
    private static final String KEY_TEST_LIST = "keyTestList";


    private TextView tv_score, tv_soni, tv_time, tv_savol;
    private RadioGroup rbG;
    private RadioButton rb1, rb2, rb3;
    private Button btn;
    private ColorStateList stateList;
    private ColorStateList timecolor;
    private CountDownTimer countDownTimer;
    private long vaqtmillisekund;
    private int testcounter = 0;
    private int testSoni = 0;
    private Test test;
    private int score;
    private boolean answered;
    private ArrayList<Test> testList;
    private long backTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tv_time = findViewById(R.id.text_test_time);
        tv_savol = findViewById(R.id.text_test_savollar);
        tv_soni = findViewById(R.id.text_test_savollar_soni);
        tv_score = findViewById(R.id.text_test_score);
        rbG = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_btn1);
        rb2 = findViewById(R.id.radio_btn2);
        rb3 = findViewById(R.id.radio_btn3);
        btn = findViewById(R.id.btn_test_javob);

        stateList = rb1.getTextColors();
        timecolor = tv_soni.getTextColors();
        if (savedInstanceState == null) {
            TestDbHelper db = new TestDbHelper(this);
            testList = db.getAllTest();
            testSoni = testList.size();
            Collections.shuffle(testList);
            showNextTest();
        } else {
            testList = savedInstanceState.getParcelableArrayList(KEY_TEST_LIST);
            testSoni = testList.size();
            testcounter = savedInstanceState.getInt(KEY_TEST_SONI);
            test = testList.get(testcounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            vaqtmillisekund = savedInstanceState.getLong(KEY_VAQT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERD);
            if (!answered) {
                startCountDown();
            } else {
                updateVaqtText();
                showSolution();
            }

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(getApplicationContext(), "Javobni tanlang", Toast.LENGTH_LONG).show();
                    }
                } else {
                    showNextTest();
                }
            }


        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_TEST_SONI, testSoni);
        outState.putLong(KEY_VAQT, vaqtmillisekund);
        outState.putBoolean(KEY_ANSWERD, answered);
        outState.putParcelableArrayList(KEY_TEST_LIST, testList);
    }

    @SuppressLint("SetTextI18n")
    private void checkAnswer() {
        answered = true;
        countDownTimer.cancel();
        RadioButton radioButton = findViewById(rbG.getCheckedRadioButtonId());
        int answerNr = rbG.indexOfChild(radioButton) + 1;
        if (answerNr == test.getAnswerNr()) {
            score++;
            tv_score.setText("Score: " + score);
        }
        showSolution();
    }

    @SuppressLint("SetTextI18n")
    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (test.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                tv_savol.setText("Answer 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                tv_savol.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                tv_savol.setText("Answer 3 is correct");
                break;

        }

        if (testcounter < testSoni) {
            btn.setText("Next");
        } else {
            btn.setText("Finish");
        }
    }

    @SuppressLint("SetTextI18n")
    private void showNextTest() {
        rb1.setTextColor(stateList);
        rb2.setTextColor(stateList);
        rb3.setTextColor(stateList);
        rbG.clearCheck();

        if (testcounter < testSoni) {
            test = testList.get(testcounter);

            tv_savol.setText(test.getSavol());
            rb1.setText(test.getJavob1());
            rb2.setText(test.getJavob2());
            rb3.setText(test.getJavob3());

            testcounter++;
            tv_soni.setText("Savollar: " + testcounter + "/" + testSoni);
            answered = false;
            btn.setText("Qabul qilindi");
            vaqtmillisekund = TEST_VAQTI;
            startCountDown();
        } else {
            finishTest();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(vaqtmillisekund, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                vaqtmillisekund = millisUntilFinished;
                updateVaqtText();
            }

            @Override
            public void onFinish() {
                vaqtmillisekund = 0;
                updateVaqtText();
                checkAnswer();
            }
        }.start();
    }

    private void updateVaqtText() {
        int minut = (int) (vaqtmillisekund / 1000) / 60;
        int sekund = (int) (vaqtmillisekund / 1000) % 60;
        String timeformat = String.format(Locale.getDefault(), "%02d:%02d", minut, sekund);
        tv_time.setText(timeformat);
        if (vaqtmillisekund < 10000) {
            tv_time.setTextColor(Color.RED);
        } else {
            tv_time.setTextColor(timecolor);
        }
    }

    private void finishTest() {
        Intent intentional = new Intent();
        intentional.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, intentional);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backTime + 2000 > System.currentTimeMillis()) {
            finishTest();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }
        backTime = System.currentTimeMillis();
    }
}
