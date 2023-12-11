// ItemDetailActivity.java
package com.example.app8;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView itemNameTextView;
    private TextView itemDescTextView;
    private TextView itemCaloriesTextView;
    private TextView itemCarbsTextView;
    private TextView itemTransTextView;
    private TextView itemSugarTextView;
    private TextView itemNaTextView;
    private TextView itemFatTextView;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        barChart = findViewById(R.id.chart);

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

            ArrayList<BarEntry> entry_chart = new ArrayList<>();
            entry_chart.add(new BarEntry(0, new float[]{
                    Float.parseFloat(item.getCarbs()),  // 탄수화물 정보를 가져와 설정
                    Float.parseFloat(item.getSugar()), // 당 정보를 가져와 설정
                    Float.parseFloat(item.getFat())  // 지방 정보를 가져와 설정
            }));

            BarDataSet barDataSet = new BarDataSet(entry_chart, "영양 정보");
            barDataSet.setColors(new int[]{Color.parseColor("#FFA726"), Color.parseColor("#66BB6A"), Color.parseColor("#29B6F6")});
            BarData barData = new BarData(barDataSet);

            barChart.setData(barData);
            barChart.setFitBars(true);
            barChart.getDescription().setEnabled(false);
            barChart.invalidate();
        } else {
            Toast.makeText(this, "아이템 정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
            // 아이템 정보가 없는 경우 처리
        }
    }
}