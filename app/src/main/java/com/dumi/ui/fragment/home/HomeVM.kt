package com.dumi.ui.fragment.home

import com.dumi.event.enums.Navigation
import com.dumi.event.eventtypes.ScreenNavigationEvent
import com.dumi.ui.BaseViewModel
import javax.inject.Inject

class HomeVM @Inject constructor() : BaseViewModel() {

    fun getWordsFromSpring(){

    }
    fun navigateToGameScreen() {
        publish(ScreenNavigationEvent(Navigation.START_GAME))
    }
}