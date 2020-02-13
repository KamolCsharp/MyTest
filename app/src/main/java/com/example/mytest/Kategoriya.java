package com.example.mytest;

import androidx.annotation.NonNull;

public class Kategoriya {
    static final int PROGRAMMING = 1;
    static final int GEOGRAPHY = 2;
    static final int MATH = 3;

    private int id;
    private String name;

    Kategoriya() {

    }

    Kategoriya(String name) {
        this.name = name;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
