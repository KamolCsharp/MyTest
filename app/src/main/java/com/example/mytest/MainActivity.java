package com.example.mytest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //region Ozgaruvchilar
    private static final int REQUEST_CODE_TEST = 1;
    public static final String EXTRA_KATEGORIYA_ID = "extraKategoriyaId";
    public static final String EXTRA_KATEGORIYA_NAME = "extraKategoriyaName";
    public static final String EXTRA_DARAJA = "qiyinlikdarajasi";

    public static final String SHARED_PREFS = "sharedprefs";
    public static final String KEY_SCORE = "keyscore";
    private TextView label;
    private int natija;
    private Spinner spennerDaraja;
    private Spinner spennerKategoriya;

    //endregion

    //region Test Start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Komponentalarni chaqirib olasiz
        label = findViewById(R.id.text_test_natija);
        spennerDaraja = findViewById(R.id.spennerDaraja);
        spennerKategoriya = findViewById(R.id.spennerKategoriya);
        Button btn = findViewById(R.id.btn_start_test);
        //endregion

        //region Malumotlarni Oqib olish
        loadDaraja();
        loadKategoriya();
        loadData();
        //endregion

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondActivity();
            }
        });
    }

    private void secondActivity() {
        Kategoriya selectkategoriya = (Kategoriya) spennerKategoriya.getSelectedItem();
        int kategoriyaId = selectkategoriya.getId();
        String kategoriyaName = selectkategoriya.getName();
        String qiyinlikD = spennerDaraja.getSelectedItem().toString();
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        intent.putExtra(EXTRA_KATEGORIYA_ID, kategoriyaId);
        intent.putExtra(EXTRA_KATEGORIYA_NAME, kategoriyaName);
        intent.putExtra(EXTRA_DARAJA, qiyinlikD);
        startActivityForResult(intent, REQUEST_CODE_TEST);
    }

    private void loadKategoriya() {
        TestDbHelper dbHelper = new TestDbHelper(getApplicationContext());
        List<Kategoriya> kategoriya = dbHelper.getKategoriya();
        ArrayAdapter<Kategoriya> adapterkategoriya = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, kategoriya);
        adapterkategoriya.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spennerKategoriya.setAdapter(adapterkategoriya);
    }

    private void loadDaraja() {
        String[] daraja = Test.getAllTestDaraja();
        ArrayAdapter<String> adapterdaraja = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, daraja);
        adapterdaraja.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spennerDaraja.setAdapter(adapterdaraja);
    }
    //endregion

    //region Test Natija Oldingi Natijadan katta bolsa ozgartirish
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_TEST == requestCode) {
            if (resultCode == RESULT_OK) {
                int _score = data.getIntExtra(TestActivity.EXTRA_SCORE, 0);
                if (_score > natija) {
                    updateNatija(_score);
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateNatija(int _score) {
        natija = _score;
        label.setText("Test Natija: " + _score);
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_SCORE, natija);
        editor.apply();
    }
    //endregion

    //region Test Natijasini Oqib Olish
    @SuppressLint("SetTextI18n")
    private void loadData() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        natija = preferences.getInt(KEY_SCORE, 0);
        label.setText("Test Natija: " + natija);
    }
    //endregion
}
