package com.acceptic.test.di.main

import com.acceptic.test.di.NativeScope
import com.acceptic.test.di.WebScope
import com.acceptic.test.layer.ui.nat.NativeFragment
import com.acceptic.test.layer.ui.web.WebFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainInjectorModule {

    @NativeScope
    @ContributesAndroidInjector(modules = [])
    fun provideNativeFragment(): NativeFragment

    @WebScope
    @ContributesAndroidInjector(modules = [])
    fun provideWebFragment(): WebFragment

}