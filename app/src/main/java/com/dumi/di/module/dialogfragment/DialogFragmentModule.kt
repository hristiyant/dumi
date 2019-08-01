package com.dumi.di.module.dialogfragment

import androidx.lifecycle.ViewModel
import com.dumi.di.module.viewmodel.ViewModelKey
import com.dumi.ui.fragment.pause.GamePausedFragment
import com.dumi.ui.fragment.pause.GamePausedVM
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DialogFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesGamePausedDialogFragment(): GamePausedFragment

    @Binds
    @IntoMap
    @ViewModelKey(GamePausedVM::class)
    abstract fun bindsGamePausedVM(vm: GamePausedVM): ViewModel
}