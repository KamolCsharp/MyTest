package com.example.mytest;

import android.os.Parcel;
import android.os.Parcelable;

public class Test implements Parcelable {
    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";

    private int id;
    private String Savol;
    private String Javob1;
    private String Javob2;
    private String Javob3;
    private int TogriJavob;
    private String difficulty;
    private int categoryID;

    //region Construktor
    public Test() {
    }

    public Test(String savol, String javob1, String javob2,
                String javob3, int answerNr, String difficulty, int categoryID) {
        Savol = savol;
        this.difficulty = difficulty;
        Javob1 = javob1;
        Javob2 = javob2;
        Javob3 = javob3;
        this.TogriJavob = answerNr;
        this.categoryID = categoryID;
    }
    //endregion

    //region Get and Set
    public String getSavol() {
        return Savol;
    }

    public void setSavol(String savol) {
        Savol = savol;
    }

    public String getJavob1() {
        return Javob1;
    }

    public void setJavob1(String javob1) {
        Javob1 = javob1;
    }

    public String getJavob2() {
        return Javob2;
    }

    public void setJavob2(String javob2) {
        Javob2 = javob2;
    }

    public String getJavob3() {
        return Javob3;
    }

    public void setJavob3(String javob3) {
        Javob3 = javob3;
    }

    public int getTogriJavob() {
        return TogriJavob;
    }

    public void setTogriJavob(int togriJavob) {
        this.TogriJavob = togriJavob;
    }

    //endregion

    @Override
    public int describeContents() {
        return 0;
    }

    protected Test(Parcel in) {
        id = in.readInt();
        Savol = in.readString();
        Javob1 = in.readString();
        Javob2 = in.readString();
        Javob3 = in.readString();
        TogriJavob = in.readInt();
        difficulty = in.readString();
        categoryID = in.readInt();
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel source) {
            return new Test(source);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Savol);
        dest.writeString(Javob1);
        dest.writeString(Javob2);
        dest.writeString(Javob3);
        dest.writeInt(TogriJavob);
        dest.writeString(difficulty);
        dest.writeInt(categoryID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public static String[] getAllTestDaraja() {
        return new String[]{
                DIFFICULTY_EASY,
                DIFFICULTY_MEDIUM,
                DIFFICULTY_HARD
        };
    }
}
