package com.example.mytest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private static final int REQUEST_CODE_TEST = 1;
    public static final String SHARED_PREFS = "sharedprefs";
    public static final String KEY_SCORE = "keyscore";
    private TextView label;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        label = findViewById(R.id.text_test_natija);
        loadData();

        btn = findViewById(R.id.btn_start_test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivityForResult(intent, REQUEST_CODE_TEST);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_TEST == requestCode) {
            if (resultCode == RESULT_OK) {
                int _score = data.getIntExtra(TestActivity.EXTRA_SCORE, 0);
                if (_score > score) {
                    updateNatija(_score);
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateNatija(int _score) {
        score = _score;
        label.setText("Test Natija: " + _score);
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_SCORE, score);
        editor.apply();
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        score = preferences.getInt(KEY_SCORE, 0);
        label.setText("Test Natija: " + score);

    }
}
