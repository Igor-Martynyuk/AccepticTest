package com.acceptic.test.layer.ui.web.abstractions

import androidx.lifecycle.LifecycleOwner

interface WebView : LifecycleOwner {
    fun notifyNativeMode()
    fun notifyWebMode()
}