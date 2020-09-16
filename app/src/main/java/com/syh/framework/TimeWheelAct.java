package com.syh.framework;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.syh.framework.algorithm.TimeWheel;
import com.syh.framework.util.BaseActivity;

/**
 * Created by shenyonghe on 2019-10-28.
 */
public class TimeWheelAct extends BaseActivity {

    private EditText editText;
    private TimeWheel timeWheel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_wheel);
        timeWheel = new TimeWheel(1, 10);
        initView();
    }

    private void initView() {
        editText = findViewById(R.id.edit_name);
        findViewById(R.id.tv_start).setOnClickListener(v -> timeWheel.start());
        findViewById(R.id.tv_stop).setOnClickListener(v -> timeWheel.stop());
        findViewById(R.id.tv_clear).setOnClickListener(v -> timeWheel.clear());
        findViewById(R.id.tv_add).setOnClickListener(v -> add());
        findViewById(R.id.tv_remove).setOnClickListener(v -> remove());
    }

    private void remove() {
        if (editText.getText() != null) {
            timeWheel.remove(editText.getText().toString());
        }
    }

    private void add() {
        if (editText.getText() != null) {
            timeWheel.add(editText.getText().toString());
        }
    }
}
