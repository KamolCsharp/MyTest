package com.example.mytest;

import android.os.Parcel;
import android.os.Parcelable;

public class Test implements Parcelable {

    //region O'zgaruvchilar
    static final String DIFFICULTY_EASY = "Easy";
    static final String DIFFICULTY_MEDIUM = "Medium";
    static final String DIFFICULTY_HARD = "Hard";

    private int id;
    private String Savol;
    private String Javob1;
    private String Javob2;
    private String Javob3;
    private int TogriJavob;
    private String Qiyinlik_darajasi;
    private int categoryID;
    //endregion

    //region Construktor
    Test() {
    }

    Test(String savol, String javob1, String javob2,
         String javob3, int answerNr, String qiyinlik_darajasi, int categoryID) {
        Savol = savol;
        this.Qiyinlik_darajasi = qiyinlik_darajasi;
        Javob1 = javob1;
        Javob2 = javob2;
        Javob3 = javob3;
        this.TogriJavob = answerNr;
        this.categoryID = categoryID;
    }
    //endregion

    //region Get and Set

    void setId(int id) {
        this.id = id;
    }

    int getCategoryID() {
        return categoryID;
    }

    void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    String getSavol() {
        return Savol;
    }

    void setSavol(String savol) {
        Savol = savol;
    }

    String getJavob1() {
        return Javob1;
    }

    void setJavob1(String javob1) {
        Javob1 = javob1;
    }

    String getJavob2() {
        return Javob2;
    }

    void setJavob2(String javob2) {
        Javob2 = javob2;
    }

    String getJavob3() {
        return Javob3;
    }

    void setJavob3(String javob3) {
        Javob3 = javob3;
    }

    int getTogriJavob() {
        return TogriJavob;
    }

    void setTogriJavob(int togriJavob) {
        this.TogriJavob = togriJavob;
    }

    //endregion

    //region Ekran aylanganda malumotlarni saqlsh uchun
    @Override
    public int describeContents() {
        return 0;
    }

    private Test(Parcel in) {
        id = in.readInt();
        Savol = in.readString();
        Javob1 = in.readString();
        Javob2 = in.readString();
        Javob3 = in.readString();
        TogriJavob = in.readInt();
        Qiyinlik_darajasi = in.readString();
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

    String getQiyinlik_darajasi() {
        return Qiyinlik_darajasi;
    }

    void setQiyinlik_darajasi(String qiyinlik_darajasi) {
        this.Qiyinlik_darajasi = qiyinlik_darajasi;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Savol);
        dest.writeString(Javob1);
        dest.writeString(Javob2);
        dest.writeString(Javob3);
        dest.writeInt(TogriJavob);
        dest.writeString(Qiyinlik_darajasi);
        dest.writeInt(categoryID);
    }
//endregion

    //region Qiyinlik Darajasi
    static String[] getAllTestDaraja() {
        return new String[]{
                DIFFICULTY_EASY,
                DIFFICULTY_MEDIUM,
                DIFFICULTY_HARD
        };
    }
    //endregion
}
