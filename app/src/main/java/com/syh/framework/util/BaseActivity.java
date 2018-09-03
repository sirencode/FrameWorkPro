package com.syh.framework.util;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created bg shenyonghe on 2018/6/4.
 */
public class BaseActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        UIParameter.setWindowStatusBarColor(this);
    }

}
