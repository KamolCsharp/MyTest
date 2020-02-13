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
import java.util.Collections;
import java.util.Locale;

public class TestActivity extends AppCompatActivity {

    //region Ozgarmas Konstanatalar
    public static final String EXTRA_SCORE = "extraScore";
    private static final long TEST_VAQTI = 30000;
    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_TEST_SONI = "keyTestSoni";
    private static final String KEY_VAQT = "keyVaqti";
    private static final String KEY_TOGRI_JAVOB = "keyAnswered";
    private static final String KEY_TEST_LIST = "keyTestList";
    //endregion

    //region Ozgaruvchilar
    private TextView tv_score;
    private TextView tv_soni;
    private TextView tv_time;
    private TextView tv_savol;
    private RadioGroup rbG;
    private RadioButton javob1, javob2, javob3;
    private Button btn;
    private ColorStateList stateList;
    private ColorStateList vaqtRangi;
    private CountDownTimer countDownTimer;
    private long vaqtmillisekund;
    private int onlineSavol = 0;
    private int savollarSoni = 0;
    private Test savol;
    private int score;
    private boolean togrijavob;
    private ArrayList<Test> savollar;
    private long backTime;
    //endregion

    //region Test
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //region Kompanentalarni Chaqirib Olish
        tv_time = findViewById(R.id.text_test_time);
        tv_savol = findViewById(R.id.text_test_savollar);
        tv_soni = findViewById(R.id.text_test_savollar_soni);
        tv_score = findViewById(R.id.text_test_score);
        TextView tv_qiyin = findViewById(R.id.text_qiyinlikdarajasi);
        TextView tv_kategoriya = findViewById(R.id.text_kategoriya);
        rbG = findViewById(R.id.radio_group);
        javob1 = findViewById(R.id.radio_btn1);
        javob2 = findViewById(R.id.radio_btn2);
        javob3 = findViewById(R.id.radio_btn3);
        btn = findViewById(R.id.btn_test_javob);
        //endregion

        stateList = javob1.getTextColors();
        vaqtRangi = tv_soni.getTextColors();

        //region MainActivitydan Malumotlani olish
        Intent intent = getIntent();
        String qiyinlik = intent.getStringExtra(MainActivity.EXTRA_DARAJA);
        int kategoriyaId = intent.getIntExtra(MainActivity.EXTRA_KATEGORIYA_ID, 0);
        String kategoriya = intent.getStringExtra(MainActivity.EXTRA_KATEGORIYA_NAME);
        tv_qiyin.setText("Daraja: " + qiyinlik);
        tv_kategoriya.setText("Kategoriya: " + kategoriya);
        //endregion

        //region Test Jarayoni
        if (savedInstanceState == null) {
            TestDbHelper db = TestDbHelper.getInstance(this);
            savollar = db.getTest(kategoriyaId, qiyinlik);
            savollarSoni = savollar.size();
            Collections.shuffle(savollar);
            showNextTest();
        } else {
            savollar = savedInstanceState.getParcelableArrayList(KEY_TEST_LIST);
            assert savollar != null;
            savollarSoni = savollar.size();
            onlineSavol = savedInstanceState.getInt(KEY_TEST_SONI);
            savol = savollar.get(onlineSavol - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            vaqtmillisekund = savedInstanceState.getLong(KEY_VAQT);
            togrijavob = savedInstanceState.getBoolean(KEY_TOGRI_JAVOB);
            if (!togrijavob) {
                startCountDown();
            } else {
                updateVaqtText();
                showSolution();
            }
        }
        //endregion

        //region Savolga Javob berish
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!togrijavob) {
                    if (javob1.isChecked() || javob2.isChecked() || javob3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(getApplicationContext(), "Javobni tanlang", Toast.LENGTH_LONG).show();
                    }
                } else {
                    showNextTest();
                }
            }


        });
        //endregion
    }
    //endregion

    //region Tozalash
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    //endregion

    //region ShaerdgaSaqlsh
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_TEST_SONI, onlineSavol);
        outState.putLong(KEY_VAQT, vaqtmillisekund);
        outState.putBoolean(KEY_TOGRI_JAVOB, togrijavob);
        outState.putParcelableArrayList(KEY_TEST_LIST, savollar);
    }
    //endregion

    //region Togri Javobni Tekshirish
    @SuppressLint("SetTextI18n")
    private void checkAnswer() {
        togrijavob = true;
        countDownTimer.cancel();
        RadioButton radioButton = findViewById(rbG.getCheckedRadioButtonId());
        int answerNr = rbG.indexOfChild(radioButton) + 1;
        if (answerNr == savol.getTogriJavob()) {
            score++;
            tv_score.setText("Score: " + score);
        }
        showSolution();
    }
    //endregion

    //region JavobTanlangandaRanglar
    @SuppressLint("SetTextI18n")
    private void showSolution() {
        javob1.setTextColor(Color.RED);
        javob2.setTextColor(Color.RED);
        javob3.setTextColor(Color.RED);

        switch (savol.getTogriJavob()) {
            case 1:
                javob1.setTextColor(Color.GREEN);
                tv_savol.setText("Answer 1 is correct");
                break;
            case 2:
                javob2.setTextColor(Color.GREEN);
                tv_savol.setText("Answer 2 is correct");
                break;
            case 3:
                javob3.setTextColor(Color.GREEN);
                tv_savol.setText("Answer 3 is correct");
                break;

        }

        if (onlineSavol < savollarSoni) {
            btn.setText("Next");
        } else {
            btn.setText("Finish");
        }
    }
    //endregion

    //region Javob Berish
    @SuppressLint("SetTextI18n")
    private void showNextTest() {
        javob1.setTextColor(stateList);
        javob2.setTextColor(stateList);
        javob3.setTextColor(stateList);
        rbG.clearCheck();
        if (onlineSavol < savollarSoni) {
            savol = savollar.get(onlineSavol);
            tv_savol.setText(savol.getSavol());
            javob1.setText(savol.getJavob1());
            javob2.setText(savol.getJavob2());
            javob3.setText(savol.getJavob3());

            onlineSavol++;
            tv_soni.setText("Savollar: " + onlineSavol + "/" + savollarSoni);
            togrijavob = false;
            btn.setText("Javob berish");
            vaqtmillisekund = TEST_VAQTI;
            startCountDown();
        } else {
            finishTest();
        }
    }
    //endregion

    //region Vaqtni Boshqarish
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
            tv_time.setTextColor(vaqtRangi);
        }
    }
    //endregion

    //region Testni Yakunlash
    private void finishTest() {
        Intent intentional = new Intent();
        intentional.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, intentional);
        finish();
    }
    //endregion

    //region Testni Yakunlamasdan Chiqish
    @Override
    public void onBackPressed() {
        if (backTime + 2000 > System.currentTimeMillis()) {
            finishTest();
        } else {
            Toast.makeText(this, "Yopishdan Oldin Testni Oxirga Yitkazing", Toast.LENGTH_SHORT).show();
        }
        backTime = System.currentTimeMillis();
    }
    //endregion
}
