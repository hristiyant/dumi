package com.dumi.ui.fragment.home

import android.util.Log
import com.dumi.event.enums.Navigation
import com.dumi.event.eventtypes.ScreenNavigationEvent
import com.dumi.networking.response.RepoResult
import com.dumi.ui.BaseViewModel
import io.reactivex.functions.Consumer
import javax.inject.Inject

class HomeVM @Inject constructor() : BaseViewModel() {

    var repoResult: RepoResult? = null

    fun getWordsFromSpring() {
        subscribeSingle(wordsApiService.getAllWordsByRootId(3), Consumer { result ->
            repoResult = result
        }, Consumer { throwable ->
            Log.e("ERROR", throwable.message)
        })
    }

    fun navigateToGameScreen() {
        publish(ScreenNavigationEvent(Navigation.START_GAME))
    }
}