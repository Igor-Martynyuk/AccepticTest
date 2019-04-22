package com.acceptic.test.di.app

import com.acceptic.test.layer.data.mode.LocalModeRepository
import com.acceptic.test.layer.data.mode.abstractions.ModeRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    @Singleton
    fun provideModeRepository(repository: LocalModeRepository): ModeRepository

}