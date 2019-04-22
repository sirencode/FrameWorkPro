package com.syh.framework.bind_life;

public interface LifeListener {
    void onStart();
    void onStop();
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();
    void onFragmentHiddenChanged (boolean isHidden);
}
