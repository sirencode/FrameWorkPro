package com.syh.framework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.syh.framework.adapter.HomeAdapters;
import com.syh.framework.view.StickyNavLayout;


public class StickLayActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticklay);

        StickyNavLayout layout = findViewById(R.id.head_home_layout);
        layout.setOnStartActivity(() -> {
            Intent intent = new Intent(StickLayActivity.this, MainActivity.class);
            startActivity(intent);
        });

        RecyclerView mHeadRecyclerView = findViewById(R.id.head_home_recyclerview);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHeadRecyclerView.setLayoutManager(layoutManager2);
        HomeAdapters mHomeAdapter = new HomeAdapters();
        mHeadRecyclerView.setAdapter(mHomeAdapter);
    }
}
