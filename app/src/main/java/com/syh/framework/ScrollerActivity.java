package com.syh.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.syh.framework.util.BaseActivity;

public class ScrollerActivity extends BaseActivity {

//    static {
//        System.load("aa");
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
    }
}
