package com.syh.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created bg shenyonghe on 2018/5/21.
 */
public class BaseLazyFragment extends Fragment {
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isVisible = false;
    private List<FragmentVisibleListener> fragmentVisibleListeners = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.isFirstVisible = true;
        this.isFirstInvisible = true;
        this.isVisible = false;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private FragmentVisibleListener listener = new FragmentVisibleListener() {
        @Override
        public void onVisibleChanged(boolean isVisible) {
            setVisibleToUser(isVisible);
        }
    };

    final void addVisibleListener(FragmentVisibleListener listener) {
        if (!fragmentVisibleListeners.contains(listener)) {
            fragmentVisibleListeners.add(listener);
        }
    }

    final void removeVisiableListener(FragmentVisibleListener listener) {
        fragmentVisibleListeners.remove(listener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initParentVisibleListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeParentVisibleListener();
    }

    private void initParentVisibleListener() {
        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof BaseLazyFragment) {
            ((BaseLazyFragment) fragment).addVisibleListener(listener);
        }
    }

    private void removeParentVisibleListener() {
        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof BaseLazyFragment) {
            ((BaseLazyFragment) fragment).removeVisiableListener(listener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setVisibleToUser(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        setVisibleToUser(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setVisibleToUser(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        setVisibleToUser(!hidden);
    }

    private boolean checkVisible() {
        Fragment fragment = getParentFragment();
        while (fragment != null) {
            if (fragment.isHidden() || !fragment.getUserVisibleHint()) {
                return false;
            }
            fragment = fragment.getParentFragment();
        }
        return !isHidden() && getUserVisibleHint();
    }

    /**
     * set visible to User
     *
     * @param isVisibleToUser
     */
    public void setVisibleToUser(boolean isVisibleToUser) {
        if (getView() != null) {
            if (isVisibleToUser) {
                if (!isVisible && checkVisible()) {
                    if (isFirstVisible) {
                        isFirstVisible = false;
                        callFirstUserVisible();
                    } else {
                        onUserVisible();
                    }
                    isVisible = isVisibleToUser;
                    notifyFragmentVisibleListeners(isVisibleToUser);
                }
            } else {
                if (isVisible) {
                    if (isFirstInvisible) {
                        isFirstInvisible = false;
                        callFirstUserInvisible();
                    } else {
                        onUserInvisible();
                    }
                    notifyFragmentVisibleListeners(isVisibleToUser);
                }
                isVisible = isVisibleToUser;
            }
        }
    }

    private void notifyFragmentVisibleListeners(boolean isVisible) {
        for (FragmentVisibleListener listener : fragmentVisibleListeners) {
            listener.onVisibleChanged(isVisible);
        }
    }

    private void callFirstUserVisible() {
        onFirstUserVisible();
        onUserVisible();
    }

    protected void onFirstUserVisible() {
//        if (presenter != null) {
//            presenter.onFirstUserVisible();
//        }
    }

    protected void onUserVisible() {
//        if (presenter != null) {
//            presenter.onUserVisible();
//        }
    }

    private void callFirstUserInvisible() {
        onUserInvisible();
        onFirstUserInvisible();
    }

    protected void onFirstUserInvisible() {
//        if (presenter != null) {
//            presenter.onFirstUserInvisible();
//        }
    }

    protected void onUserInvisible() {
//        if (presenter != null) {
//            presenter.onUserInvisible();
//        }
    }

    public boolean isVisibleToUser() {
        return isVisible;
    }

    private  interface FragmentVisibleListener {
        /**
         * visibility变化回调
         *
         * @param isVisible
         */
        void onVisibleChanged(boolean isVisible);
    }
}
