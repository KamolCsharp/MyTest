package com.example.mytest;

import android.provider.BaseColumns;

 final class TestContract {
    private TestContract() {
    }

     static class Kategoriya implements BaseColumns {
         static final String TABLE_NAME = "test_kategoriya";
         static final String COL_NAME = "categoriya";
    }

     static class TestTable implements BaseColumns {
         static final String TABLE_NAME = "test_savollar";
         static final String COL_SAVOL = "savollar";
         static final String COL_JAVOB1 = "javob1";
         static final String COL_JAVOB2 = "javob2";
         static final String COL_JAVOB3 = "javob3";
         static final String COL_NUMBER = "answer_nr";
         static final String COL_DIFFICULTY = "difficulty";
         static final String COL_CATEGORY_ID = "category_id";

    }
}
