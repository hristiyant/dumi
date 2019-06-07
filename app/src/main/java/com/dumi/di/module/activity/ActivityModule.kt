package com.dumi.di.module.activity

import androidx.lifecycle.ViewModel
import com.dumi.di.module.viewmodel.ViewModelKey
import com.dumi.ui.activity.main.MainActivity
import com.dumi.ui.activity.main.MainVM
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainVM::class)
    abstract fun bindsMainVM(vm: MainVM): ViewModel
}