package com.acceptic.test.layer.ui.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.acceptic.test.di.main.MainScope
import com.acceptic.test.layer.core.navigator.Navigator
import com.acceptic.test.layer.ui.abstractions.Presenter
import com.acceptic.test.layer.ui.main.abstractions.MainView
import com.acceptic.test.layer.ui.nat.abstractions.NativeView
import com.acceptic.test.layer.ui.web.abstractions.WebView
import javax.inject.Inject

@MainScope
class MainPresenter @Inject constructor(private val navigator: Navigator) : Presenter<MainView>() {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onViewResumed() {
        this.navigator.fragment.forward(this.view, NativeView::class.java)
        this.navigator.fragment.forward(this.view, WebView::class.java)
    }

}