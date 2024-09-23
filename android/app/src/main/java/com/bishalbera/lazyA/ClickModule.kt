package com.bishalbera.lazyA

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class ClickModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    private val service: ClickService? = null

    override fun getName(): String {
        return "ClickModule"
    }
    @ReactMethod
    fun performClick(x: Int, y: Int, promise: Promise)
    {
       service?.performGlobalClick(x, y)
        promise.resolve(true)
    }
}