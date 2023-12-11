package com.example.app8;

import java.util.Date;

public class EatDB {
    public double calories;
    public double protein;
    public double carbs;
    public double fat;
    public String name;

    public EatDB() {
    }
    public EatDB(double calories, double protein, double carbs, double fat, String name) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.name = name;


    }
    public String getStrTN() {
        return name;
    }
}
