package com.syh.framework.bind_life;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class ListenerFragment extends Fragment {
    private LifeListener mlistener;
    public static final String ListenerFragmentTag = "ListenerFragment";

    public ListenerFragment () {

    }

    public void setLifeListener (LifeListener mlistener) {
        this.mlistener = mlistener;
    }

    public LifeListener getLifeListener() {
        return mlistener;
    }

    @Override
    public void onCreate (@Nullable final Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if (mlistener != null) {
            mlistener.onCreate();
        }
    }

    @Override
    public void onResume () {
        super.onResume ();
        if (mlistener != null) {
            mlistener.onResume ();
        }
    }

    @Override
    public void onHiddenChanged (final boolean hidden) {
        super.onHiddenChanged (hidden);
        if (mlistener != null) {
            mlistener.onFragmentHiddenChanged (hidden);
        }
    }

    @Override
    public void onPause () {
        if (mlistener != null) {
            mlistener.onPause ();
        }
        super.onPause ();
    }

    @Override
    public void onStart () {
        super.onStart ();
        if (mlistener != null) {
            mlistener.onStart ();
        }
    }

    @Override
    public void onStop () {
        if (mlistener != null) {
            mlistener.onStop ();
        }
        super.onStop ();
    }

    @Override
    public void onDestroy () {
        if (mlistener != null) {
            mlistener.onDestroy ();
        }
        super.onDestroy ();
    }
}
