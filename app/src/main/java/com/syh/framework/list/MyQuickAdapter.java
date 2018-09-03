package com.syh.framework.list;

import android.widget.TextView;


import com.syh.framework.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created bg shenyonghe on 2018/6/8.
 */
public class MyQuickAdapter extends BaseQuickAdapter<String> {

    private static final HashMap<Integer, Integer> map;

    static {
        map = new HashMap<>();
        map.put(0, R.layout.item_test_list);
        map.put(1, R.layout.item_test2_list);
    }

    public MyQuickAdapter(List<String> data) {
        super(map, data);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? 0 : 1;
    }

    @Override
    public void convert(BaseViewHolder holder, String s) {
        TextView title = holder.getView(R.id.tv_title);
        title.setText(s);
    }
}