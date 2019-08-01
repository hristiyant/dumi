package com.dumi.di.module.fragment

import androidx.lifecycle.ViewModel
import com.dumi.di.module.viewmodel.ViewModelKey
import com.dumi.ui.fragment.SharedGameVM
import com.dumi.ui.fragment.endgame.EndGameFragment
import com.dumi.ui.fragment.game.GameFragment
import com.dumi.ui.fragment.help.HelpFragment
import com.dumi.ui.fragment.help.HelpVM
import com.dumi.ui.fragment.home.HomeFragment
import com.dumi.ui.fragment.home.HomeVM
import com.dumi.ui.fragment.loading.LoadingGameFragment
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
    @ViewModelKey(SharedGameVM::class)
    abstract fun bindsSharedGameVM(vm: SharedGameVM): ViewModel

    @ContributesAndroidInjector
    abstract fun contributesProfileFragment(): ProfileFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProfileVM::class)
    abstract fun bindsProfileVM(vm: ProfileVM): ViewModel

    @ContributesAndroidInjector
    abstract fun contributesHelpFragment(): HelpFragment

    @Binds
    @IntoMap
    @ViewModelKey(HelpVM::class)
    abstract fun bindsHelpVM(vm: HelpVM): ViewModel

    @ContributesAndroidInjector
    abstract fun contributesLoadingGameFragment(): LoadingGameFragment

    @ContributesAndroidInjector
    abstract fun contributesEndGameFragment(): EndGameFragment
}