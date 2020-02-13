package com.example.mytest;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import static com.example.mytest.TestContract.TestTable;

public class TestDbHelper extends SQLiteOpenHelper {

    //region Ozgaruvchilar
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;
    private static TestDbHelper instance;
    private SQLiteDatabase db;
    //endregion

    //region Konstruktor
     TestDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //endregion

    //region Bazani sinxron Ishlashi uchun
    static synchronized TestDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new TestDbHelper(context.getApplicationContext());
        }
        return instance;
    }
    //endregion

    //region Yangi Jadval Yaratish
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_TEST_CATEGORY = "CREATE TABLE " +
                TestContract.Kategoriya.TABLE_NAME + " (" +
                TestContract.Kategoriya._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TestContract.Kategoriya.COL_NAME + " TEXT )";

        final String SQL_CREATE_TEST_TABLE = "CREATE TABLE " +
                TestTable.TABLE_NAME + " ( " +
                TestTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TestTable.COL_SAVOL + " TEXT, " +
                TestTable.COL_JAVOB1 + " TEXT, " +
                TestTable.COL_JAVOB2 + " TEXT, " +
                TestTable.COL_JAVOB3 + " TEXT, " +
                TestTable.COL_NUMBER + " INTEGER, " +
                TestTable.COL_DIFFICULTY + " TEXT, " +
                TestTable.COL_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + TestTable.COL_CATEGORY_ID + ") REFERENCES " +
                TestContract.Kategoriya.TABLE_NAME + "(" +
                TestContract.Kategoriya._ID + ")" +
                "ON DELETE CASCADE" +
                ");";

        db.execSQL(SQL_CREATE_TEST_TABLE);
        db.execSQL(SQL_CREATE_TEST_CATEGORY);
        fillKategoriyaTable();
        fillQuestionsTable();
    }
    //endregion

    //region Kategoriya Malumotlar
    private void fillKategoriyaTable() {
        Kategoriya k1 = new Kategoriya("Programming");
        addKategoriya(k1);
        Kategoriya k2 = new Kategoriya("Geography");
        addKategoriya(k2);
        Kategoriya k3 = new Kategoriya("Math");
        addKategoriya(k3);
    }
    //endregion

    //region Kategoriyaga Yangi Malumot Qoshish
    private void addKategoriya(Kategoriya k) {
        ContentValues cv = new ContentValues();
        cv.put(TestContract.Kategoriya.COL_NAME, k.getName());
        db.insert(TestContract.Kategoriya.TABLE_NAME, null, cv);
    }
    //endregion

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    //region Malumotlar
    private void fillQuestionsTable() {
        Test test1 = new Test("Programming, Easy: int a=0;a++;\n Natija nechchi?",
                "0", "1", "2", 1,
                Test.DIFFICULTY_EASY, Kategoriya.PROGRAMMING);
        AddTest(test1);
        Test test2 = new Test("Programming, Easy: int a=20%2? 1:0;\n Natija nechchi?",
                "3", "0", "1", 2,
                Test.DIFFICULTY_EASY, Kategoriya.PROGRAMMING);
        AddTest(test2);
        Test test3 = new Test("Geography, Medium: Rossiyaning Poytaxti?",
                "Moskova", "UFA", "Sankt-Petirburg", 1,
                Test.DIFFICULTY_MEDIUM, Kategoriya.GEOGRAPHY);
        AddTest(test3);
        Test test4 = new Test("Geography, Medium: O'zbekistonning Poytaxti?",
                "Samarqand", "Buxoro", "Toshkent", 3,
                Test.DIFFICULTY_MEDIUM, Kategoriya.GEOGRAPHY);
        AddTest(test4);
        Test test5 = new Test("Math, Hard: 14+8=?",
                "12", "22", "30", 2,
                Test.DIFFICULTY_HARD, Kategoriya.MATH);
        AddTest(test5);
        Test test6 = new Test("Math, Hard: 7+8=?",
                "15", "11", "16", 1,
                Test.DIFFICULTY_HARD, Kategoriya.MATH);
        AddTest(test6);
    }
    //endregion

    //region Testga Yangi Malumot Qoshish
    private void AddTest(Test test) {
        ContentValues cv = new ContentValues();
        cv.put(TestTable.COL_SAVOL, test.getSavol());
        cv.put(TestTable.COL_JAVOB1, test.getJavob1());
        cv.put(TestTable.COL_JAVOB2, test.getJavob2());
        cv.put(TestTable.COL_JAVOB3, test.getJavob3());
        cv.put(TestTable.COL_NUMBER, test.getTogriJavob());
        cv.put(TestTable.COL_DIFFICULTY, test.getQiyinlik_darajasi());
        cv.put(TestTable.COL_CATEGORY_ID, test.getCategoryID());
        db.insert(TestTable.TABLE_NAME, null, cv);
    }
    //endregion

    //region Bazadagi Jadvalni bor yoki Yoqligini tekshirish
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TestTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TestContract.Kategoriya.TABLE_NAME);
        onCreate(db);
    }
    //endregion

    //region Kategoriya Malumotlarni Oqib Olish
    @SuppressLint("Recycle")
    ArrayList<Kategoriya> getKategoriya() {
        ArrayList<Kategoriya> testList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TestContract.Kategoriya.TABLE_NAME , null);

        if (cursor.moveToFirst()) {
            do {
                Kategoriya test = new Kategoriya();
                test.setId(cursor.getInt(cursor.getColumnIndex(TestContract.Kategoriya._ID)));
                test.setName(cursor.getString(cursor.getColumnIndex(TestContract.Kategoriya.COL_NAME)));
                testList.add(test);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return testList;
    }
    //endregion

    //region Testlarni Oqib Olish
    ArrayList<Test> getTest(int kategory, String daraja) {
        ArrayList<Test> testList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = TestTable.COL_CATEGORY_ID + " = ? " +
                " AND " + TestTable.COL_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(kategory), daraja};

        Cursor cursor = db.query(
                TestTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                Test test = new Test();
                test.setId(cursor.getInt(cursor.getColumnIndex(TestTable.COL_CATEGORY_ID)));
                test.setSavol(cursor.getString(cursor.getColumnIndex(TestTable.COL_SAVOL)));
                test.setJavob1(cursor.getString(cursor.getColumnIndex(TestTable.COL_JAVOB1)));
                test.setJavob2(cursor.getString(cursor.getColumnIndex(TestTable.COL_JAVOB2)));
                test.setJavob3(cursor.getString(cursor.getColumnIndex(TestTable.COL_JAVOB3)));
                test.setTogriJavob(cursor.getInt(cursor.getColumnIndex(TestTable.COL_NUMBER)));
                test.setQiyinlik_darajasi(cursor.getString(cursor.getColumnIndex(TestTable.COL_DIFFICULTY)));
                test.setCategoryID(cursor.getInt(cursor.getColumnIndex(TestTable.COL_CATEGORY_ID)));
                testList.add(test);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return testList;
    }
    //endregion
}
//java.lang.outofMemoryError;
//outofMemoryError thrown while trying to throw
//outofMemoryError; no stack trace available