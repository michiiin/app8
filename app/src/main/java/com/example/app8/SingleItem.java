package com.example.app8;


import android.util.EventLogTags;

import java.io.Serializable;

public class SingleItem implements Serializable {
    public String name;
    public String description;
    private String calories;
    private String carbs;
    private String sugar;
    private String fat;
    private String trans;
    private String na;
    public int resId;

    public SingleItem(String name, String description, int resId) {
        this.name = name;
        this.description = description;
        this.resId = resId;
    }// 클래스 내용

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public String getCalories() {
        return calories;
    }

    public String getCarbs() {
        return carbs;
    }

    public String getSugar() {
        return sugar;
    }
    public String getFat() {
        return fat;
    }
    public String getTrans() {
        return trans;
    }
    public String getNa() {
        return na;
    }

    public void setDetails(String name, String description, String calories, String carbs,String na, String fat, String trans) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.carbs = carbs;
        this.na = na;
        this.fat = fat;
        this.trans = trans;
    }
}
