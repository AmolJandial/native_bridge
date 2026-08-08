package com.hitagi.native_bridge

import android.app.Activity

interface ActivityAwareComponent {
    fun onActivityAvailable(activity: Activity){}
    fun onActivityUnavailable(){}
}