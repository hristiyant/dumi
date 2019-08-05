package com.dumi.di.module.application

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return application
    }
}