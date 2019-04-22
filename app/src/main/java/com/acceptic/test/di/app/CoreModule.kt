package com.acceptic.test.di.app

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.acceptic.test.layer.core.navigator.Navigator
import com.acceptic.test.layer.ui.main.MainActivity
import com.acceptic.test.layer.ui.main.abstractions.MainView
import com.acceptic.test.layer.ui.nat.NativeFragment
import com.acceptic.test.layer.ui.nat.abstractions.NativeView
import com.acceptic.test.layer.ui.web.WebFragment
import com.acceptic.test.layer.ui.web.abstractions.WebView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule {

    @Singleton
    @Provides
    fun provideSPrefs(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun provideNavigator(context: Context) = Navigator(
        context,
        mapOf(
            MainView::class.java to MainActivity::class.java
        ),
        mapOf(
            NativeView::class.java to NativeFragment::class.java,
            WebView::class.java to WebFragment::class.java
        )
    )

}