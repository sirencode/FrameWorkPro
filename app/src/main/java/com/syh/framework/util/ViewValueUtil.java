package com.syh.framework.util;

import android.text.TextUtils;
import android.widget.TextView;

public class ViewValueUtil {

    public static void setText(TextView view, String text) {
        if (view != null && !TextUtils.isEmpty(text)) {
            view.setText(text);
        }
    }
}
