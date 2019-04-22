package com.acceptic.test.layer.ui.abstractions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable

@Suppress("MemberVisibilityCanBePrivate")
abstract class Presenter<V> : LifecycleObserver where V : LifecycleOwner {
    protected var disposables: CompositeDisposable? = null

    protected lateinit var view: V

    fun attachView(view: V) {
        this.view = view
        this.view.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume() {
        this.disposables = CompositeDisposable()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause() {
        this.disposables?.dispose()
    }

}