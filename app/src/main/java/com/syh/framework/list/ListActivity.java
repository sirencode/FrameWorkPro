package com.syh.framework.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.syh.framework.R;
import com.syh.framework.util.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created bg shenyonghe on 2018/6/7.
 */
public class ListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MyQuickAdapter adapter;
    private int num = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initRV();
    }

    private void initRV() {
        recyclerView = findViewById(R.id.recycler_view);
        refreshLayout = findViewById(R.id.refreshLay);
        refreshLayout.setOnRefreshListener(() -> {
            refreshList();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyQuickAdapter(getData());
        recyclerView.setAdapter(adapter);
    }

    private List<ItemDemo> getData() {
        List<ItemDemo> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ItemDemo itemDemo = new ItemDemo();
            itemDemo.setName("shen" + i);
            itemDemo.setKeyNum(i + "key");
            list.add(itemDemo);
        }
        return list;
    }


    private List<ItemDemo> getRefreshData() {
        List<ItemDemo> list = getData();
        list.remove(4);
        list.remove(5);
        list.remove(6);
        return list;
    }

    private void refreshList() {
        adapter.setDatas(getRefreshData());
        refreshLayout.setRefreshing(false);
    }
}
