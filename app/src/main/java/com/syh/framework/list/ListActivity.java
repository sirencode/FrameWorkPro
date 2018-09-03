package com.syh.framework.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initRV();
    }

    private void initRV() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyQuickAdapter adapter = new MyQuickAdapter(getData());
        recyclerView.setAdapter(adapter);
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("hello" + i);
        }
        return list;
    }
}
