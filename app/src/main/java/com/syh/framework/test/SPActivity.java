package com.syh.framework.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.syh.framework.R;
import com.syh.framework.util.SPHelper;


/**
 * Created bg shenyonghe on 2018/6/7.
 */
public class SPActivity extends Activity {

    private TextView infoView;
    private SPHelper helper;
    public static final String KEY = "test_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        initView();
    }

    private void initView() {
        helper = new SPHelper(this, "test");
        infoView = findViewById(R.id.tv_spinfo);
        findViewById(R.id.tv_save).setOnClickListener(v -> saveData());
        findViewById(R.id.tv_get).setOnClickListener(v -> getData());
        findViewById(R.id.tv_clear).setOnClickListener(v -> clearData());
    }

    private void saveData() {
        helper.put(KEY, 123456);
    }

    private void getData() {
        int value = (int) helper.getValue(KEY, 0);
        infoView.setText(String.valueOf(value));
    }

    private void clearData() {
        helper.clear();
    }
}
