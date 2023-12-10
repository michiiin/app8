// ItemDetailActivity.java
package com.example.app8;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView itemNameTextView;
    private TextView itemDescTextView;
    private TextView itemCaloriesTextView;
    private TextView itemCarbsTextView;
    private TextView itemTransTextView;
    private TextView itemSugarTextView;
    private TextView itemNaTextView;
    private TextView ItemTransTextViews;
    private TextView itemFatTextView;

    public ItemDetailActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailactivity);

        // 아이템 정보를 가져와서 표시하는 코드
        SingleItem item = getIntent().getParcelableExtra("itemInfo");

        itemNameTextView = findViewById(R.id.snack_name);
        itemDescTextView = findViewById(R.id.descTxt);
        itemCaloriesTextView = findViewById(R.id.calrorie);
        itemCarbsTextView = findViewById(R.id.calbon);
        itemSugarTextView = findViewById(R.id.sugar);
        itemTransTextView = findViewById(R.id.transfat);
        itemNaTextView = findViewById(R.id.na);
        itemFatTextView = findViewById(R.id.fat);

        if (item != null) {
            // 가져온 아이템 정보를 화면에 표시
            itemNameTextView.setText(item.getName());
            itemDescTextView.setText(item.getDescription());
            itemCaloriesTextView.setText(item.getCalories());
            itemCarbsTextView.setText(item.getCarbs());
            itemSugarTextView.setText(item.getSugar());
            itemTransTextView.setText(item.getTrans());
            itemNaTextView.setText(item.getNa());
            itemFatTextView.setText(item.getFat());
        } else {

        }

    }
}