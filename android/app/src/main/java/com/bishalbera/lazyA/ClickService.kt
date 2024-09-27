package com.bishalbera.lazyA

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class ClickService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event != null) {
            if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED || event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED){
                Log.d("ACCESSIBILITY", "Event: ${event.eventType}")

                val rootNode: AccessibilityNodeInfo? = rootInActiveWindow
                if (rootNode != null){
                    traverseWindowContent(rootNode)
                }
            }
        }
    }

    override fun onInterrupt() {
    }
    fun performGlobalClick(x: Int, y: Int)
    {
        val nodeInfo: AccessibilityNodeInfo = rootInActiveWindow ?: return
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }


    // Traverse the window and capture text from UI elements
    private fun traverseWindowContent(node: AccessibilityNodeInfo?) {
        if (node == null) return

        val text = node.text
        if (text !=null){
            Log.d("ACCESSIBILITY","Captured text: $text")
        }

        for (i in 0 until node.childCount) {
            traverseWindowContent(node.getChild(i))
        }
    }
}