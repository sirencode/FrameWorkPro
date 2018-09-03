package com.syh.framework.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.syh.framework.R;


/**
 * Created bg shenyonghe on 2018/5/21.
 */
public class UIParameter {

    private static DisplayMetrics displayMetrics;
    private static int width;
    private static int height;
    private static float density;

    private static DisplayMetrics getDisplayMetrics(Context context) {
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }
        return displayMetrics;
    }

    private static float getDensity(Context context) {
        if (density == 0) {
            density = getDisplayMetrics(context).density;
        }
        return density;
    }

    public static int getWidth(Context context) {
        if (width == 0) {
            width = getDisplayMetrics(context).widthPixels;
        }
        return width;
    }

    public static int getHeight(Context context) {
        if (height == 0) {
            height = getDisplayMetrics(context).heightPixels;
        }
        return height;
    }

    public static int dp2px(Context context, int dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5f);
    }

    public static int px2dp(Context context, int pxValue) {
        return (int) (pxValue / getDensity(context) + 0.5f);
    }

    public static void setWindowStatusBarColor(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimary));

                //底部导航栏
                window.setNavigationBarColor(activity.getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
