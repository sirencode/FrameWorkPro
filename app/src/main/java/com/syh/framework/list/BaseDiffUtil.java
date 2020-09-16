package com.syh.framework.list;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class BaseDiffUtil extends DiffUtil.Callback {
    private final List<DiffKey> oldList;
    private final List<DiffKey> newList;

    public BaseDiffUtil(List<DiffKey> oldEmployeeList, List<DiffKey> newEmployeeList) {
        this.oldList = oldEmployeeList;
        this.newList = newEmployeeList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getType() == newList.get(newItemPosition).getType();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final DiffKey oldEmployee = oldList.get(oldItemPosition);
        final DiffKey newEmployee = newList.get(newItemPosition);

        return oldEmployee.getKey().equals(newEmployee.getKey());
    }

    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
