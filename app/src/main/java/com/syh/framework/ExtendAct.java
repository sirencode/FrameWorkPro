package com.syh.framework;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.syh.framework.view.ExpandLayout;
import com.syh.framework.view.FloatingViewManager;

/**
 * created by 申永鹤(shenyonghe@theduapp.com) on 2020/7/6
 **/
public class ExtendAct extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend);
        init();
    }

    private void init() {
        ExpandLayout view = findViewById(R.id.expandLayout);
        ExpandLayout view1 = findViewById(R.id.expandLayout1);
        view.initExpand(false);
        view1.initExpand(false);
        LinearLayout container = findViewById(R.id.lay_extend_container);
        LinearLayout container1 = findViewById(R.id.lay_extend_container1);

        findViewById(R.id.button).setOnClickListener(view2 -> {
            FloatingViewManager.INSTANCE.updateLogMsg("btn_extend");
            view.toggleExpand();
        });

        findViewById(R.id.btn_extend1).setOnClickListener(view2 -> {
            FloatingViewManager.INSTANCE.updateLogMsg("http://112.124.194.76:8080/mock/132/api_goods/skuDetail/v1.0?token={token}&timestamp={timestamp}");
            view1.toggleExpand();
        });

        for (int i = 0; i < 20; i++) {
            View linearLayout =  LayoutInflater.from(this).inflate(R.layout.item_extend_item, container, false);
            TextView lef = linearLayout.findViewById(R.id.tv_extend_item_left);
            TextView right = linearLayout.findViewById(R.id.tv_extend_item_right);
            lef.setText("Left"+i);
            right.setText("Right"+i);
            container.addView(linearLayout);
        }

        for (int i = 0; i < 10; i++) {
            View linearLayout =  LayoutInflater.from(this).inflate(R.layout.item_extend_item, container1, false);
            TextView lef = linearLayout.findViewById(R.id.tv_extend_item_left);
            TextView right = linearLayout.findViewById(R.id.tv_extend_item_right);
            lef.setText("Left"+i);
            right.setText("Right"+i);
            container1.addView(linearLayout);
        }
    }

}
