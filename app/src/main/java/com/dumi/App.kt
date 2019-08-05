package com.dumi

import com.dumi.di.component.DaggerAppComponent
import com.dumi.di.module.application.ApplicationModule
import com.dumi.di.module.network.NetworkingModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .networkingModule(NetworkingModule())
            .build()
    }
}