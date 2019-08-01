package com.dumi.ui.fragment.pause

import com.dumi.event.enums.Navigation
import com.dumi.event.eventtypes.ScreenNavigationEvent
import com.dumi.ui.BaseViewModel
import javax.inject.Inject

class GamePausedVM @Inject constructor() : BaseViewModel() {

    fun onResumeClick() {
        publish(ScreenNavigationEvent(Navigation.RESUME))
    }

    fun onHelpClick() {
        publish(ScreenNavigationEvent(Navigation.HELP))
    }

    fun onQuitClick() {
        publish(ScreenNavigationEvent(Navigation.QUIT_GAME))
    }

    fun onRestartClick() {
        publish(ScreenNavigationEvent(Navigation.RESTART))
    }
}