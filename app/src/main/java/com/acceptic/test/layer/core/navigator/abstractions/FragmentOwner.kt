package com.acceptic.test.layer.core.navigator.abstractions

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

interface FragmentOwner {
    fun getActualFragmentManager(): FragmentManager
    fun getParentFor(target: Class<out LifecycleOwner>): ViewGroup
}