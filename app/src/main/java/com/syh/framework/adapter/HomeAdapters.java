package com.syh.framework.adapter;


import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syh.framework.R;
import com.syh.framework.util.UIParameter;

public class HomeAdapters extends RecyclerView.Adapter<HomeAdapters.ViewHolder>{

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_homes, parent, false);
        ViewGroup.LayoutParams params = mView.getLayoutParams();
        params.width = UIParameter.getWidth(parent.getContext()) / 4 + 1;
        mView.setLayoutParams(params);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 11;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view) {
            super(view);
        }
    }
}
