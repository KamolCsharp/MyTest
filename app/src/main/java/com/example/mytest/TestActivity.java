package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private TextView tv_score, tv_soni, tv_time, tv_savol;
    private RadioGroup rbG;
    private RadioButton rb1, rb2, rb3;
    private Button btn;
    private ColorStateList stateList;
    private int testcounter = 0;
    private int testSoni = 0;
    private Test test;
    private int score;
    private boolean answered;
    private List<Test> testList;

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

        TestDbHelper db = new TestDbHelper(this);
        testList = db.getAllTest();
        testSoni = testList.size();
        Collections.shuffle(testList);
        showNextTest();

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

    @SuppressLint("SetTextI18n")
    private void checkAnswer() {
        answered = true;
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
        }else{
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
        } else {
            finishTest();
        }
    }

    private void finishTest() {
        finish();
    }
}
