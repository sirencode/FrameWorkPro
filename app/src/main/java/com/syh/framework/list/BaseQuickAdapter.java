package com.syh.framework.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created bg shenyonghe on 2018/6/7.
 */
public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public List<T> datas;
    public Context context;
    public HashMap<Integer, Integer> map;
    private int itemLayoutId;

    public BaseQuickAdapter(HashMap<Integer, Integer> hashMap, List<T> data) {
        this.datas = data == null ? new ArrayList<>() : data;
        this.map = hashMap == null ? new HashMap<>() : hashMap;
    }

    public BaseQuickAdapter(int itemLayoutId, List<T> data) {
        this.datas = data == null ? new ArrayList<>() : data;
        this.itemLayoutId = itemLayoutId;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        int layoutId = map == null ? itemLayoutId : map.get(viewType);
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return BaseViewHolder.getHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        convert(holder, datas.get(position));
    }

    public abstract void convert(BaseViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    /**
     * 数据重置
     *
     * @param list
     */
    public void setDatas(@NonNull List<T> list) {
        this.datas.clear();
        this.datas = list == null ? new ArrayList<>() : list;
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        this.datas.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加单个数据
     *
     * @param t
     */
    public void addData(T t) {
        if (t != null) {
            this.datas.add(t);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加多个数据
     *
     * @param list
     */
    public void addDatas(List<T> list) {
        if (list != null && list.size() > 0) {
            this.datas.addAll(list);
            notifyDataSetChanged();
        }

    }

}
