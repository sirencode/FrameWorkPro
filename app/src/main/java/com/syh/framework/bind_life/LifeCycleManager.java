package com.syh.framework.bind_life;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;

public class LifeCycleManager {
    public static void bindLife (Object mContext, LifeListener mListener) {
        if (mContext instanceof Activity) {
            FragmentManager manager = ((Activity) mContext).getFragmentManager ();
            ListenerFragment fragment = (ListenerFragment) manager.findFragmentByTag(ListenerFragment.ListenerFragmentTag);
            if (fragment == null) {
                fragment = new ListenerFragment ();
                fragment.setLifeListener (mListener);
                manager.beginTransaction ().add (fragment, ListenerFragment.ListenerFragmentTag).commitAllowingStateLoss ();
            } else {
                mListener = fragment.getLifeListener();
            }
        } else if (mContext instanceof FragmentActivity) {
            android.support.v4.app.FragmentManager manager = ((FragmentActivity) mContext).getSupportFragmentManager ();
            ListenerFragmentV4 listenerFragment = (ListenerFragmentV4) manager.findFragmentByTag(ListenerFragmentV4.ListenerFragmentTag);
            if (listenerFragment == null) {
                listenerFragment = new ListenerFragmentV4 ();
                listenerFragment.setLifeListener (mListener);
                manager.beginTransaction().add (listenerFragment, ListenerFragmentV4.ListenerFragmentTag).commitAllowingStateLoss ();
            } else {
                mListener = listenerFragment.getLifeListener();
            }
        } else if (mContext instanceof Fragment) {
            FragmentManager manager = ((Fragment) mContext).getFragmentManager ();
            ListenerFragment fragment = (ListenerFragment) manager.findFragmentByTag(ListenerFragment.ListenerFragmentTag);
            if (fragment == null) {
                fragment = new ListenerFragment ();
                fragment.setLifeListener (mListener);
                manager.beginTransaction ().add (fragment, ListenerFragment.ListenerFragmentTag).commitAllowingStateLoss ();
            } else {
                mListener = fragment.getLifeListener();
            }
        } else if (mContext instanceof android.support.v4.app.Fragment) {
            android.support.v4.app.FragmentManager manager = ((android.support.v4.app.Fragment) mContext).getFragmentManager ();
            ListenerFragmentV4 listenerFragment = (ListenerFragmentV4) manager.findFragmentByTag(ListenerFragmentV4.ListenerFragmentTag);
            if (listenerFragment == null) {
                listenerFragment = new ListenerFragmentV4 ();
                listenerFragment.setLifeListener (mListener);
                manager.beginTransaction().add (listenerFragment, ListenerFragmentV4.ListenerFragmentTag).commitAllowingStateLoss ();
            } else {
                mListener = listenerFragment.getLifeListener();
            }
        }
    }
}
