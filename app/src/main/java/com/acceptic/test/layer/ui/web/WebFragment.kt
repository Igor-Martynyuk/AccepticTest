package com.acceptic.test.layer.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acceptic.test.layer.ui.web.abstractions.WebView as IWebView
import androidx.fragment.app.Fragment
import com.acceptic.test.R
import com.acceptic.test.sdk.TargetWebView

import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

@Suppress("ProtectedInFinal")
class WebFragment : Fragment(), IWebView, TargetWebListener {
    private lateinit var webView: TargetWebView

    @Inject
    protected lateinit var presenter: WebPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        this.presenter.attachView(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.webView = view.findViewById(R.id.web)
        this.webView.setListener(this)

    }

    override fun notifyNativeMode() = this.webView.setBtnBackgroundColor(android.R.color.holo_red_light)
    override fun notifyWebMode() = this.webView.setBtnBackgroundColor(android.R.color.holo_green_light)
    override fun onBtnClicked() = this.presenter.onSetWebModeCommand()

    override fun onPageLoaded() {
        this.webView.setup(
            android.R.color.tab_indicator_text,
            getString(R.string.button_upper_case),
            resources.getDimension(R.dimen.margin_large) / resources.displayMetrics.density
        )
        this.presenter.onLoaded()
    }

}
