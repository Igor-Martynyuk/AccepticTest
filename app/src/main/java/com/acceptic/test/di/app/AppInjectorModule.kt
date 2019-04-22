package com.acceptic.test.di.app

import com.acceptic.test.di.main.MainInjectorModule
import com.acceptic.test.di.main.MainScope
import dagger.Module
import com.acceptic.test.layer.ui.main.MainActivity
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class AppInjectorModule {

    @MainScope
    @ContributesAndroidInjector(modules = [MainInjectorModule::class])
    abstract fun provideMainViewInjector(): MainActivity

}