package com.bishalbera.lazyA

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.facebook.react.ReactApplication
import java.lang.StringBuilder

class ClickService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("ACCESSIBILITY","Accessibility service connected")
    }
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event != null) {
            Log.d("ACCESSIBILITY", "Event received: ${AccessibilityEvent.eventTypeToString(event.eventType)}") // Log event type

            if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED || event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED){
                Log.d("ACCESSIBILITY", "Relevant event: ${AccessibilityEvent.eventTypeToString(event.eventType)}")

                val rootNode: AccessibilityNodeInfo? = rootInActiveWindow
                if (rootNode != null){
                     Log.d("ACCESSIBILITY", "Root node found, traversing the window content...")
                     Log.d("ACCESSIBILITY", "Root node class: ${rootNode.className}")


                    val capturedText = traverseWindowContent(rootNode)
                    sendCapturedTextToReactNative(capturedText)
                } else{
                    Log.d("ACCESSIBILITY", "Root node is null")
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
    private fun traverseWindowContent(node: AccessibilityNodeInfo?): String {
        if (node == null) return ""
 
        val text = node.text
        val contentDescription = node.contentDescription

        val capturedText = StringBuilder()

        if (!text.isNullOrEmpty()){
            Log.d("ACCESSIBILITY","Captured text: $text")
            capturedText.append(text).append("")
        } else if (!contentDescription.isNullOrEmpty()) {
            Log.d("ACCESSIBILITY", "Captured contentDescription: $contentDescription")
            capturedText.append(contentDescription).append(" ")

        }else {
            Log.d("ACCESSIBILITY", "No text found in node")
        }
        

        for (i in 0 until node.childCount) {
            capturedText.append(traverseWindowContent(node.getChild(i)))
        }
        return capturedText.toString()
    }

    private fun sendCapturedTextToReactNative(capturedText: String) {
        Log.d("ACCESSIBILITY", "Sending captured text to React Native: $capturedText")

        val reactApp = application as ReactApplication
        val reactContext = reactApp.reactNativeHost.reactInstanceManager.currentReactContext

        if (reactContext != null) {
            val clickModule = reactContext.getNativeModule(ClickModule::class.java)
            clickModule?.sendEvent("CapturedText",capturedText)
        } else{
            Log.d("ACCESSIBILITY","React Context is null")
        }
    }

//    fun captureUIContent(): String {
//    val rootNode = rootInActiveWindow // Get the root node
//    if (rootNode == null) {
//        Log.d("ACCESSIBILITY", "Root node is null")
//        return ""
//    }
//
//    // Traverse the window content and capture text
//    return traverseWindowContent(rootNode)
//}


}
