package com.example.mytest;

import android.os.Parcel;
import android.os.Parcelable;

public class Test implements Parcelable {
    public static final String DIFFICULTY_EASY = "Easy";
   // public static final String DIFFICULTY_EASY = "Easy";
   // public static final String DIFFICULTY_EASY = "Easy";

    private String Savol;
    private String Javob1;
    private String Javob2;
    private String Javob3;
    private int answerNr;

    public Test() {
    }

    public Test(String savol, String javob1, String javob2, String javob3, int answerNr) {
        Savol = savol;
        Javob1 = javob1;
        Javob2 = javob2;
        Javob3 = javob3;
        this.answerNr = answerNr;
    }

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

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Test(Parcel in) {
        Savol = in.readString();
        Javob1 = in.readString();
        Javob2 = in.readString();
        Javob3 = in.readString();
        answerNr = in.readInt();
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Savol);
        dest.writeString(Javob1);
        dest.writeString(Javob2);
        dest.writeString(Javob3);
        dest.writeInt(answerNr);
    }
}
