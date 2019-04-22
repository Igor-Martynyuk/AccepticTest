package com.acceptic.test

import android.app.Activity
import android.app.Application
import com.acceptic.test.di.app.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .context(this.applicationContext)
            .build()
            .inject(this)

    }

    override fun activityInjector() = this.dispatchingAndroidInjector
}