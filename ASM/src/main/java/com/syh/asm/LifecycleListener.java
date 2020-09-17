package com.syh.asm;

import java.util.List;

/**
 * Created by shenyonghe on 2020/9/17.
 */
public interface LifecycleListener {
    void add(PathRecord record);

    List<PathRecord> getAll();
}
