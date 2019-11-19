package com.dumi.ui.fragment

import android.os.CountDownTimer
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.dumi.adapter.item.WordItem
import com.dumi.event.enums.InGame
import com.dumi.event.enums.Navigation
import com.dumi.event.eventtypes.DisplayWordEvent
import com.dumi.event.eventtypes.InGameEvent
import com.dumi.event.eventtypes.ScreenNavigationEvent
import com.dumi.model.Prefix
import com.dumi.ui.BaseViewModel
import com.dumi.util.GAME_TIMER_START_VALUE
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashSet
import kotlin.coroutines.CoroutineContext

class SharedGameVM @Inject constructor() : BaseViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    val gameLevelsLiveData = MutableLiveData<MutableList<Prefix>>()

    var levelsCounter = 0
    var totalScore: MutableLiveData<Int> = MutableLiveData(0)
    var startWordsMap: MutableLiveData<Map<Int, String>> = MutableLiveData()

    private var countDownTimer: CountDownTimer? = null
    private var isTimeRunning: Boolean = false
    private var timeLeftInMillis = GAME_TIMER_START_VALUE
    var timeLeftFormatted = MutableLiveData<String>()

    var dumi = ObservableArrayList<WordItem>()
    var allEntries = ObservableArrayList<String>()

    fun fetchGameLevels() {
        scope.launch {
            val gameLevels = wordsApiService.getLevels()
            gameLevelsLiveData.postValue(gameLevels)
        }
        //invoke on completion navigate to game screen
    }

    fun cancelAllRequests() {
        coroutineContext.cancel()
    }

    fun onPauseClick() {
        pauseTimer()
        publish(ScreenNavigationEvent(Navigation.GAME_PAUSED))
    }

    fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onFinish() {
                levelsCounter++
                if (levelsCounter < 3) {
                    publish(ScreenNavigationEvent(Navigation.NEXT_LEVEL))
                } else {
                    isTimeRunning = false
                    publish(ScreenNavigationEvent(Navigation.END_GAME))
                    resetGameVariables()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }
        }.start()

        isTimeRunning = true
    }

    fun checkWord(word: String) {
        val newWordAsObject = WordItem(word)

        var words: HashSet<String> = HashSet()

        var wordsFromApi = gameLevelsLiveData.value?.get(levelsCounter)?.words
        wordsFromApi?.forEach {
            words.add(it.word)
        }
        if (!allEntries.contains(word)) {
            if (words.contains(word)) {

                //Word is valid, print it on screen with white font.
                newWordAsObject.setIsCorrect(true)
                increaseTotalScore(word.length * 100)
            }

            showWord(newWordAsObject)
        } else {
            //show txtAlreadyMentionedWarning
            showAlreadyMentioned()
        }
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        isTimeRunning = false
    }

    fun resetTimer() {
        timeLeftInMillis = GAME_TIMER_START_VALUE
        updateCountDownText()
    }

    private fun updateCountDownText() {
        val minutes = ((timeLeftInMillis / 1000) / 60).toInt()
        val seconds = ((timeLeftInMillis / 1000) % 60).toInt()

        timeLeftFormatted.value = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    fun loadGameLevels(newList: HashMap<Int, String>) {
        startWordsMap.value = newList
    }

    fun increaseTotalScore(points: Int) {
        totalScore.value = totalScore.value?.plus(points)
    }

    fun resetGameVariables() {
        levelsCounter = 0
        resetTimer()
        dumi.clear()
        allEntries.clear()
        totalScore.postValue(0)
    }

    private fun showWord(word: WordItem) {
        publish(DisplayWordEvent(word))
    }

    private fun showAlreadyMentioned() {
        publish(InGameEvent(InGame.ALREADY_MENTIONED))
    }

    override fun onCleared() {
        super.onCleared()
    }
}