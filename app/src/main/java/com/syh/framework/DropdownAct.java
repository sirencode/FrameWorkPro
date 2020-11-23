package com.syh.framework;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.syh.framework.util.BaseActivity;
import com.syh.framework.view.DropdownLayout;

public class DropdownAct extends BaseActivity {

    private DropdownLayout dropdownLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);
        dropdownLayout = findViewById(R.id.dropdown_layout);
//        findViewById(R.id.toggle).setOnClickListener(v -> dropdownLayout.toggle());
        findViewById(R.id.tv_toggle).setOnClickListener(v -> dropdownLayout.toggle());
    }
}
