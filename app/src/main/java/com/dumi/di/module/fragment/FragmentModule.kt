package com.dumi.di.module.fragment

import androidx.lifecycle.ViewModel
import com.dumi.di.module.viewmodel.ViewModelKey
import com.dumi.ui.fragment.game.GameFragment
import com.dumi.ui.fragment.game.GameVM
import com.dumi.ui.fragment.home.HomeFragment
import com.dumi.ui.fragment.home.HomeVM
import com.dumi.ui.fragment.profile.ProfileFragment
import com.dumi.ui.fragment.profile.ProfileVM
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesHomeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeVM::class)
    abstract fun bindsHomeVM(vm: HomeVM): ViewModel

    @ContributesAndroidInjector
    abstract fun contributesGameFragment(): GameFragment

    @Binds
    @IntoMap
    @ViewModelKey(GameVM::class)
    abstract fun bindsGameVM(vm: GameVM): ViewModel

    @ContributesAndroidInjector
    abstract fun contributesProfileFragment(): ProfileFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProfileVM::class)
    abstract fun bindsProfilenVM(vm: ProfileVM): ViewModel
}