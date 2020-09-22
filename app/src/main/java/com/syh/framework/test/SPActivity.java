package com.syh.framework.test;

import android.os.Bundle;
import android.widget.TextView;

import com.syh.framework.R;
import com.syh.framework.util.BaseActivity;
import com.syh.framework.util.spwithtime.SPData;
import com.syh.framework.util.spwithtime.SpTimeHelper;


/**
 * Created bg shenyonghe on 2018/6/7.
 */
public class SPActivity extends BaseActivity {

    private TextView infoView;
    private SpTimeHelper helper;
    public static final String KEY = "test_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        initView();
    }

    private void initView() {
        helper = new SpTimeHelper(this, "test");
        infoView = findViewById(R.id.tv_spinfo);
        findViewById(R.id.tv_save).setOnClickListener(v -> saveData());
        findViewById(R.id.tv_get).setOnClickListener(v -> getData());
        findViewById(R.id.tv_clear).setOnClickListener(v -> clearData());
    }

    private void saveData() {
        SPData data = new SPData(11, 2, SpTimeHelper.MINUTE, System.currentTimeMillis());
        helper.put(KEY, data);
    }

    private void getData() {
        int value = (int) helper.getValue(KEY, 0);
        infoView.setText(String.valueOf(value));
    }

    private void clearData() {
        helper.clear();
    }
}
