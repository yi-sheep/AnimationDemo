package com.gaoxianglong.animationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private View mBtn_top_left;
    private View mBtn_top_right;
    private View mBtn_center;
    private View mBtn_under_left;
    private View mBtn_under_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mBtn_top_left = findViewById(R.id.button_top_left);
        mBtn_top_right = findViewById(R.id.button_top_right);
        mBtn_center = findViewById(R.id.button_center);
        mBtn_under_left = findViewById(R.id.button_under_left);
        mBtn_under_right = findViewById(R.id.button_under_right);
    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, ImgActivity.class);
        int[] location = new int[2]; // 用于保存控件的坐标
        switch (view.getId()) {
            case R.id.button_top_left:
                mBtn_top_left.getLocationOnScreen(location);
                break;
            case R.id.button_top_right:
                mBtn_top_right.getLocationOnScreen(location);
                break;
            case R.id.button_center:
                mBtn_center.getLocationOnScreen(location);
                break;
            case R.id.button_under_left:
                mBtn_under_left.getLocationOnScreen(location);
                break;
            case R.id.button_under_right:
                mBtn_under_right.getLocationOnScreen(location);
                break;
        }
        intent.putExtra(ImgActivity.POINTX, location[0] + mBtn_center.getMeasuredWidth() / 2);
        intent.putExtra(ImgActivity.POINTY, location[1] + mBtn_center.getMeasuredHeight() / 2);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}
