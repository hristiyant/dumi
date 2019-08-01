package com.dumi.di.component

import com.dumi.di.module.activity.ActivityModule
import com.dumi.di.module.application.ApplicationModule
import com.dumi.di.module.dialogfragment.DialogFragmentModule
import com.dumi.di.module.fragment.FragmentModule
import com.dumi.di.module.viewmodel.DaggerViewModelInjectionModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        DaggerViewModelInjectionModule::class,
        ActivityModule::class,
        FragmentModule::class,
        DialogFragmentModule::class,
        ApplicationModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication>