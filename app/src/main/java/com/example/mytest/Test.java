package com.example.mytest;

public class Test {
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
}
