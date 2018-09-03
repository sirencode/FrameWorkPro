package com.syh.framework.util;

import android.view.View;

public class FindViewUtil {
    public static View getView(View root, int id) {
        return root.findViewById(id);
    }
}
