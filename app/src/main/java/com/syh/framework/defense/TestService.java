package com.syh.framework.defense;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by shenyonghe on 2020/4/22.
 */
public class TestService extends AccessibilityService {

    private static final String TAG = "TestService";

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.i(TAG, "onServiceConnected");
    }



    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, "-------------------------------------------------------------");
        int eventType = event.getEventType(); //事件类型
        Log.i(TAG, "PackageName:" + event.getPackageName() + ""); // 响应事件的包名
        Log.i(TAG, "Source Class:" + event.getClassName() + ""); // 事件源的类名
        Log.i(TAG, "Description:" + event.getContentDescription() + ""); // 事件源描述
        Log.i(TAG, "Event Type(int):" + eventType + "");
        if (event.getSource() != null && !TextUtils.isEmpty(event.getSource().getText())) {
            Log.i(TAG, "Event Text:" + event.getSource().getText());
        }

        if (AccessibilityEvent.TYPE_VIEW_CLICKED == eventType && "可监听点击".equals(event.getSource().getText())) {
            List<AccessibilityNodeInfo> list  = getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.syh.accessibilitydefense:id/btn_can_sim_listen");
            List<AccessibilityNodeInfo> list1  = getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.syh.accessibilitydefense:id/btn_cannot_sim_listen");
//            for (AccessibilityNodeInfo info : list) {
//                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            }

            for (AccessibilityNodeInfo nodeInfo : list1) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
