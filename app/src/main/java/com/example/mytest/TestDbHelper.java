package com.example.mytest;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.mytest.TestContract.*;


public class TestDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public TestDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_TEST_TABLE = "CREATE TABLE " +
                TestTable.TABLE_NAME + " ( " +
                TestTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TestTable.COL_SAVOL + " TEXT, " +
                TestTable.COL_JAVOB1 + " TEXT, " +
                TestTable.COL_JAVOB2 + " TEXT, " +
                TestTable.COL_JAVOB3 + " TEXT, " +
                TestTable.COL_NUMBER + " INTEGER);";

        db.execSQL(SQL_CREATE_TEST_TABLE);
        fillQuestionsTable();
    }

    private void fillQuestionsTable() {
        Test test1 = new Test("A is correct", "A", "B", "C", 1);
        AddTest(test1);
        Test test2 = new Test("B is correct", "A", "B", "C", 2);
        AddTest(test2);
        Test test3 = new Test("C is correct", "A", "B", "C", 3);
        AddTest(test3);
        Test test4 = new Test("A is correct again", "A", "B", "C", 1);
        AddTest(test4);
        Test test5 = new Test("A is correct again", "A", "B", "C", 2);
        AddTest(test5);
    }

    private void AddTest(Test test) {
        ContentValues cv = new ContentValues();
        cv.put(TestTable.COL_SAVOL, test.getSavol());
        cv.put(TestTable.COL_JAVOB1, test.getJavob1());
        cv.put(TestTable.COL_JAVOB2, test.getJavob2());
        cv.put(TestTable.COL_JAVOB3, test.getJavob3());
        cv.put(TestTable.COL_NUMBER, test.getAnswerNr());
        db.insert(TestTable.TABLE_NAME, null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TestTable.TABLE_NAME);
        onCreate(db);
    }

    @SuppressLint("Recycle")
    public List<Test> getAllTest() {
        List<Test> testList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TestTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Test test = new Test();
                test.setSavol(cursor.getString(cursor.getColumnIndex(TestTable.COL_SAVOL)));
                test.setJavob1(cursor.getString(cursor.getColumnIndex(TestTable.COL_JAVOB1)));
                test.setJavob2(cursor.getString(cursor.getColumnIndex(TestTable.COL_JAVOB2)));
                test.setJavob3(cursor.getString(cursor.getColumnIndex(TestTable.COL_JAVOB3)));
                test.setAnswerNr(cursor.getInt(cursor.getColumnIndex(TestTable.COL_NUMBER)));
                testList.add(test);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return testList;
    }
}
