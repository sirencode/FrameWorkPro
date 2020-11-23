package com.syh.framework;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.syh.framework.util.BaseActivity;
import com.syh.framework.util.ShadowDrawable;


/**
 * Created bg shenyonghe on 2018/7/12.
 */
public class ShadowAct extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);
        TextView textView1 = findViewById(R.id.text1);
        TextView textView2 = findViewById(R.id.text2);
        TextView textView3 = findViewById(R.id.text3);
        TextView textView4 = findViewById(R.id.text4);
        TextView textView5 = findViewById(R.id.text5);
        ShadowDrawable.setShadowDrawable(textView1, Color.parseColor("#3D5AFE"), dpToPx(8),
                Color.parseColor("#66000000"), dpToPx(10), 0, 0);
        ShadowDrawable.setShadowDrawable(textView2, Color.parseColor("#2979FF"), dpToPx(8),
                Color.parseColor("#992979FF"), dpToPx(6), 0, dpToPx(4));
        ShadowDrawable.setShadowDrawable(textView3, new int[]{
                        Color.parseColor("#536DFE"), Color.parseColor("#7C4DFF")}, dpToPx(8),
                Color.parseColor("#997C4DFF"), dpToPx(5), dpToPx(5), dpToPx(5));
        ShadowDrawable.setShadowDrawable(textView4, ShadowDrawable.SHAPE_CIRCLE, Color.parseColor("#1DE9B6"),
                0, Color.parseColor("#99FF3D00"), dpToPx(8), 0, 0);
        ShadowDrawable.setShadowDrawable(textView5, ShadowDrawable.SHAPE_CIRCLE, Color.parseColor("#FF3D00"),
                dpToPx(8), Color.parseColor("#991DE9B6"), dpToPx(6), dpToPx(4), dpToPx(4));
    }

    private int dpToPx(int dp) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dp + 0.5f);
    }
}
