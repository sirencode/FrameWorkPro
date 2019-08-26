package com.syh.framework.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class AutoLoadOnScrollListener extends RecyclerView.OnScrollListener {

    private AutoLoad autoLoad;
    private int lastCount = 0;
    private int lave = 5;

    public AutoLoadOnScrollListener(AutoLoad autoLoad) {
        this.autoLoad = autoLoad;
    }

    public AutoLoadOnScrollListener(AutoLoad autoLoad, int lave) {
        this.autoLoad = autoLoad;
        this.lave = lave;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //判断是当前layoutManager是否为LinearLayoutManager
        // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            //获取最后一个可见view的位置
            int lastItemPosition = linearManager.findLastVisibleItemPosition();
            //获取第一个可见view的位置
            int currentCount = recyclerView.getAdapter().getItemCount();
            if (lave < currentCount && lastItemPosition >= currentCount - lave) {
                if (autoLoad != null && currentCount > lastCount) {
                    autoLoad.autoLoad();
                    lastCount = currentCount;
                    System.out.println("start autoLoad");
                }
            }
        }
    }

    public interface AutoLoad {
        void autoLoad();
    }
}

