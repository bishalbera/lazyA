package com.bishalbera.lazyA

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class ClickService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    }

    override fun onInterrupt() {
    }
    fun performGlobalClick(x: Int, y: Int)
    {
        val nodeInfo: AccessibilityNodeInfo = rootInActiveWindow ?: return
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }
}