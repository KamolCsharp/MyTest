package com.example.mytest;

import android.provider.BaseColumns;

public final class TestContract {
    private TestContract() {
    }

    public static class TestTable implements BaseColumns {
        public static final String TABLE_NAME = "test_savollar";
        public static final String COL_SAVOL = "savollar";
        public static final String COL_JAVOB1 = "javob1";
        public static final String COL_JAVOB2 = "javob2";
        public static final String COL_JAVOB3 = "javob3";
        public static final String COL_NUMBER = "answer_nr";
        public static final String COL_DIFFICULTY = "difficulty";

    }
}
