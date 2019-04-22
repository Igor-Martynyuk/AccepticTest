package com.acceptic.test.layer.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.acceptic.test.R
import com.acceptic.test.layer.ui.main.abstractions.MainView
import com.acceptic.test.layer.ui.nat.abstractions.NativeView
import com.acceptic.test.layer.ui.web.abstractions.WebView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

@Suppress("ProtectedInFinal")
class MainActivity : AppCompatActivity(), MainView, HasSupportFragmentInjector {
    private lateinit var cardTop: CardView
    private lateinit var cardBottom: CardView

    @Inject
    protected lateinit var injector: DispatchingAndroidInjector<Fragment>
    @Inject
    protected lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        this.presenter.attachView(this)

        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_main)
        this.cardTop = this.findViewById(R.id.card_top)
        this.cardBottom = this.findViewById(R.id.card_bottom)
    }

    override fun getActualFragmentManager() = supportFragmentManager
    override fun getParentFor(target: Class<out LifecycleOwner>) = when (target) {
        NativeView::class.java -> this.cardTop
        WebView::class.java -> this.cardBottom
        else -> throw IllegalArgumentException("Invalid target class")
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = this.injector

}
