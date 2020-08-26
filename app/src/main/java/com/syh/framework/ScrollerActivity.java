package com.syh.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.syh.framework.util.BaseActivity;
import com.syh.plugin.annotation.Cost;

public class ScrollerActivity extends BaseActivity {

//    static {
//        System.load("aa");
//    }

    @Cost
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
    }
}
