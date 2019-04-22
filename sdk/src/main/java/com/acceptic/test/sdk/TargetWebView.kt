package com.acceptic.test.sdk

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.acceptic.test.layer.ui.web.TargetWebListener

@SuppressLint("SetJavaScriptEnabled")
class TargetWebView : FrameLayout {

    private val webView: WebView
    private var listener: TargetWebListener? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.webView = WebView(context)
        this.webView.layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        this.addView(webView)
        this.webView.settings.javaScriptEnabled = true
        this.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                listener?.onPageLoaded()
            }
        }
        this.webView.webChromeClient = WebChromeClient()
        this.webView.addJavascriptInterface(this, "Interface")
        this.webView.loadUrl("file:///android_asset/web.html")
    }

    private fun convertToHex(resId: Int): String {
        val hex = String.format("#%06X", ContextCompat.getColor(context!!, resId))
        return hex.removeRange(1, 3)
    }

    fun setListener(listener: TargetWebListener) {
        this.listener = listener
    }

    fun setup(titleColor: Int, btnText: String, btnPadding: Float) {
        this.webView.loadUrl("javascript:init('${convertToHex(titleColor)}', '$btnText', '${btnPadding}px')")
    }

    fun setBtnBackgroundColor(value: Int) =
        this.webView.loadUrl("javascript:setBtnBackgroundColor('${convertToHex(value)}')")

    @Suppress("unused")
    @JavascriptInterface
    fun onBtnClick() = listener?.onBtnClicked()

}