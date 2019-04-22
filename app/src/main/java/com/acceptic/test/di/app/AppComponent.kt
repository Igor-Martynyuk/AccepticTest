package com.acceptic.test.di.app

import android.content.Context
import com.acceptic.test.TestApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppInjectorModule::class, CoreModule::class, DataModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent

    }

    fun inject(app: TestApp)

}