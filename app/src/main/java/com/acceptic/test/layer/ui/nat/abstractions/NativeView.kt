package com.acceptic.test.layer.ui.nat.abstractions

import androidx.lifecycle.LifecycleOwner

interface NativeView : LifecycleOwner{
    fun notifyNativeMode()
    fun notifyWebMode()
}