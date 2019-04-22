package com.acceptic.test.layer.ui.nat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import com.acceptic.test.R
import com.acceptic.test.layer.ui.nat.abstractions.NativeView
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

@Suppress("ProtectedInFinal")
class NativeFragment : Fragment(), NativeView {
    private lateinit var btn: TextView

    @Inject
    protected lateinit var presenter: NativePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        this.presenter.attachView(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_native, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.btn = view.findViewById(R.id.btn)
        this.btn.setOnClickListener { this.presenter.onSetNativeModeCommand() }
    }

    override fun notifyNativeMode() =
        this.btn.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.holo_red_light))

    override fun notifyWebMode() =
        this.btn.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.holo_green_light))

}
