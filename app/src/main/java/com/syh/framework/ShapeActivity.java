package com.syh.framework;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import cn.mtjsoft.www.shapeview.ShapeTextView;

public class ShapeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shape_activity_main);

        ShapeTextView shapeTextView = findViewById(R.id.stv);
        // 代码动态设置
        shapeTextView.setSolidColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
        shapeTextView.setRadius(10);
        // 设置生效
        shapeTextView.setCustomBackground();
    }
}
