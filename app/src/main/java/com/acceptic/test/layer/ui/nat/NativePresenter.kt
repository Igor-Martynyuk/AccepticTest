package com.acceptic.test.layer.ui.nat

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.acceptic.test.di.NativeScope
import com.acceptic.test.layer.core.entities.Mode
import com.acceptic.test.layer.domain.ChangeModeCase
import com.acceptic.test.layer.domain.ObserveModeCase
import com.acceptic.test.layer.ui.abstractions.Presenter
import com.acceptic.test.layer.ui.nat.abstractions.NativeView
import javax.inject.Inject

@NativeScope
class NativePresenter @Inject constructor(
    private val observeModeCase: ObserveModeCase,
    private val changeModeCase: ChangeModeCase
) : Presenter<NativeView>() {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onViewResumed() {
        this.observeModeCase.execute(
            onNext = { value: Mode ->
                when (value) {
                    Mode.NATIVE -> this.view.notifyNativeMode()
                    Mode.WEB -> this.view.notifyWebMode()
                }
            },
            onError = { t -> t.printStackTrace() }
        )
    }

    fun onSetNativeModeCommand() {
        this.disposables!!.add(
            this.changeModeCase.execute(
                Mode.NATIVE,
                onError = { t -> t.printStackTrace() }
            )
        )
    }

}