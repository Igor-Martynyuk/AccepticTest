package com.acceptic.test.layer.ui.web

import com.acceptic.test.di.WebScope
import com.acceptic.test.layer.core.entities.Mode
import com.acceptic.test.layer.domain.ChangeModeCase
import com.acceptic.test.layer.domain.ObserveModeCase
import com.acceptic.test.layer.ui.abstractions.Presenter
import com.acceptic.test.layer.ui.web.abstractions.WebView
import javax.inject.Inject

@WebScope
class WebPresenter @Inject constructor(
    private val observeModeCase: ObserveModeCase,
    private val changeModeCase: ChangeModeCase
) : Presenter<WebView>() {

    fun onLoaded() {
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

    fun onSetWebModeCommand() {
        this.disposables!!.add(
            this.changeModeCase.execute(
                Mode.WEB,
                onError = { t -> t.printStackTrace() }
            )
        )
    }

}