package com.bishalbera.lazyA

import android.content.Intent
import android.os.Build
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.modules.core.DeviceEventManagerModule

class ClickModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    private val service: ClickService? = null
    private var reactContext: ReactApplicationContext = reactContext


    override fun getName(): String {
        return "ClickModule"
    }
    @ReactMethod
    fun performClick(x: Int, y: Int, promise: Promise)
    {
       service?.performGlobalClick(x, y)
        promise.resolve(true)
    }

    @ReactMethod
    fun startForegroundService(){
        val intent = Intent(reactApplicationContext, ForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            reactApplicationContext.startForegroundService(intent)
        } else{
            reactApplicationContext.startService(intent)
        }
    }

    @ReactMethod
    fun stopForegroundService(){
        val intent = Intent(reactApplicationContext, ForegroundService::class.java)
        reactApplicationContext.stopService(intent)
    }

    fun sendEvent(eventName: String, params: String){
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName,params)
    }

//    @ReactMethod
//    fun captureUIContent(promise: Promise) {
////        val service = this.service // Ensure the service is running
//        if (service != null) {
//            val capturedText = service.captureUIContent() // This should call the method in ClickService to traverse the UI and return captured text
//            promise.resolve(capturedText)
//        } else {
//            promise.reject("SERVICE_NOT_RUNNING", "Accessibility service is not running")
//        }
//    }
}