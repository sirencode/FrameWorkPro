package om.syh.framework.accessibilitydefense

import android.view.View
import android.view.accessibility.AccessibilityEvent

/**
 * Created by shenyonghe on 2020/5/21.
 */
class DefenseAccessibilityDelegate : View.AccessibilityDelegate() {
    override fun sendAccessibilityEvent(host: View?, eventType: Int) {
        if (eventType != AccessibilityEvent.TYPE_VIEW_CLICKED) {
            super.sendAccessibilityEvent(host, eventType)
        }
    }
}