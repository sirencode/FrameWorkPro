package com.syh.framework;

import android.os.Bundle;
import android.view.View;

import com.syh.framework.util.BaseActivity;
import com.syh.framework.view.ClearScreenLayout;


public class ClearScreenActivity extends BaseActivity implements View.OnClickListener {
    private ClearScreenLayout layoutClear;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_screen);
        init();
    }

    private void init() {
        layoutClear = findViewById(R.id.layoutClear);
        layoutClear.setSlideDirection(ClearScreenLayout.SlideDirection.RIGHT);
        View viewWhite = findViewById(R.id.viewWhite);
        View ll12 = findViewById(R.id.ll12);
        View tv3 = findViewById(R.id.tv3);
        View viewBg = findViewById(R.id.viewBg);
        layoutClear.addClearViews(viewWhite, ll12, tv3, viewBg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClearWithAnim:
                layoutClear.clearWithAnim();
                break;
            case R.id.btnRestoreWithAnim:
                layoutClear.restoreWithAnim();
                break;
            case R.id.btnClearNoAnim:
                layoutClear.clearWithoutAnim();
                break;
            case R.id.btnRestoreNoAnim:
                layoutClear.restoreWithoutAnim();
                break;
        }
    }
}
