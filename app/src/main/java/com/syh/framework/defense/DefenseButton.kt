package om.syh.framework.accessibilitydefense

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button

/**
 * Created by shenyonghe on 2020/5/21.
 */
class DefenseButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : Button(context, attrs, defStyleAttr) {

    override fun performAccessibilityAction(action: Int, arguments: Bundle?): Boolean {
        if (action == AccessibilityNodeInfo.ACTION_CLICK){
            return true;
        } else{
            return super.performAccessibilityAction(action, arguments)
        }
    }
}