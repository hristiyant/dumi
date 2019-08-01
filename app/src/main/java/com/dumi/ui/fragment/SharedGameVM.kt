package com.dumi.ui.fragment

import android.os.CountDownTimer
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.dumi.adapter.item.WordItem
import com.dumi.event.enums.Navigation
import com.dumi.event.eventtypes.ScreenNavigationEvent
import com.dumi.ui.BaseViewModel
import java.util.*
import javax.inject.Inject

const val START_TIME_IN_MILLIS: Long = 5000

class SharedGameVM @Inject constructor() : BaseViewModel() {

    var totalScore: MutableLiveData<Int> = MutableLiveData<Int>().apply { postValue(0) }
    //    var currentLevelScore = 0
    var levelsCounter = 0

    var startWords: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var startWordsSet: MutableLiveData<HashSet<String>> = MutableLiveData()
    var startWordsMap: MutableLiveData<Map<Int, String>> = MutableLiveData()

    private var countDownTimer: CountDownTimer? = null
    private var isTimeRunning: Boolean = false
    private var timeLeftInMillis = START_TIME_IN_MILLIS
    var timeLeftFormatted = MutableLiveData<String>()

    var dumi = ObservableArrayList<WordItem>()
    var allEntries = ObservableArrayList<String>()
    var secondsRemaining = MutableLiveData<Long>()

    //Add and populate a list of already mentioned words.
    var dictionaryBulgarian = arrayListOf(
        "СТО",
        "СТОБОР",
        "СТОВАРВАМ",
        "СТОВАРВАНЕ",
        "СТОВАРЯ",
        "СТОВАРЯМ",
        "СТОВАРЯНЕ",
        "СТОГЛАВ",
        "СТОГОДИШЕН",
        "СТОГОДИШНИНА",
        "СТОДОЛАРОВ",
        "СТОЕЖ",
        "СТОЕНЕ",
        "СТОЕШКОМ",
        "СТОЖЕР",
        "СТОИК",
        "СТОИЦИЗЪМ",
        "СТОИЧЕСКИ",
        "СТОЙКА",
        "СТОЙНОСТ",
        "СТОЙНОСТЕН",
        "СТОКА",
        "СТОКОВ",
        "СТОКОВЕД",
        "СТОКОВЕДКА",
        "СТОКОЗНАНИЕ",
        "СТОКООБМЕН",
        "СТОКООБОРОТ",
        "СТОКОПРОИЗВОДИТЕЛ",
        "СТОКРАТЕН",
        "СТОКРАТНО",
        "СТОЛ",
        "СТОЛАР",
        "СТОЛАРСКИ",
        "СТОЛАРСТВО",
        "СТОЛЕВКА",
        "СТОЛЕТЕН",
        "СТОЛЕТИЕ",
        "СТОЛЕТНИК",
        "СТОЛЕТНИЦА",
        "СТОЛИСТЕН",
        "СТОЛИЦА",
        "СТОЛИЧАНИН",
        "СТОЛИЧАНКА",
        "СТОЛИЧЕН",
        "СТОЛОВА",
        "СТОЛОВАТ",
        "СТОЛУВАМ",
        "СТОЛУВАНЕ",
        "СТОЛЧЕ",
        "СТОМАНА",
        "СТОМАНЕН",
        "СТОМАНОБЕТОН",
        "СТОМАНОЛЕЯР",
        "СТОМАНОЛЕЯРЕН",
        "СТОМАНОЛЕЯРНА",
        "СТОМАТИТ",
        "СТОМАТОЛОГ",
        "СТОМАТОЛОГИЧЕН",
        "СТОМАТОЛОГИЯ",
        "СТОМАТОСКОП",
        "СТОМАХ",
        "СТОМАШЕН",
        "СТОМЕТРОВ",
        "СТОМНА",
        "СТОН",
        "СТОНОГА",
        "СТОНОЖКА",
        "СТОП",
        "СТОПАДЖИЙСКИ",
        "СТОПАДЖИЯ",
        "СТОПАНИН",
        "СТОПАНИСАМ",
        "СТОПАНИСАНЕ",
        "СТОПАНИСВАМ",
        "СТОПАНИСВАНЕ",
        "СТОПАНКА",
        "СТОПАНОВЕД",
        "СТОПАНОВЕДКА",
        "СТОПАНСКИ",
        "СТОПАНСТВЕН",
        "СТОПАНСТВО",
        "СТОПИРАМ",
        "СТОПЛЯ",
        "СТОПЛЯМ",
        "СТОПЛЯНЕ",
        "СТОПРОЦЕНТОВ",
        "СТОПРОЦЕНТОВО",
        "СТОПЯ",
        "СТОПЯВАМ",
        "СТОПЯВАНЕ",
        "СТОПЯЕМОСТ",
        "СТОРВАМ",
        "СТОРЯ",
        "СТОТАК",
        "СТОТАРКА",
        "СТОТАЧКА",
        "СТОТЕН",
        "СТОТИНА",
        "СТОТИНКА",
        "СТОТИЦА",
        "СТОТНА",
        "СТОТНИК",
        "СТОХИЛЯДЕН",
        "СТОХИЛЯДНИК",
        "СТОЧЕН",
        "СТОЯ",
        "СТОЯЛКА",
        "СТОЯЛО",
        "DRIP",
        "DRIB",
        "DRIVE",
        "DRINK",
        "DRILL",
        "DRIED",
        "DRIFT",
        "DRIES",
        "DRIER",
        "DRIBS",
        "DRILY",
        "DRIPT",
        "DRIPS",
        "DRIVEN",
        "DRIVER",
        "DRIEST",
        "DRIVEL",
        "DRIPPY",
        "DRINKS",
        "DRIVES",
        "DRIERS",
        "DRIEGH",
        "DRILLS",
        "DRIFTY",
        "DRIFTS",
        "DRIVING",
        "DRIZZLE",
        "DRIBBLE",
        "DRINKER",
        "DRIFTER",
        "DRIBLET",
        "DRIBBED",
        "DRIBBLY",
        "DRIFTED",
        "DRILLED",
        "DRILLER",
        "DRIPPED",
        "DRIPPER",
        "DRIZZLY",
        "DRIVERS",
        "DRIVELS",
        "DRIPPING",
        "DRIVEWAY",
        "DRIPLESS",
        "DRIFTAGE",
        "DRIFTERS",
        "DRIFTIER",
        "DRIFTING",
        "DRIBLETS",
        "DRIBBING",
        "DRIBBLED",
        "DRIBBLER",
        "DRIBBLES",
        "DRIBBLET",
        "DRILLERS",
        "DRILLING",
        "DRIFTPIN",
        "DRINKERS",
        "DRINKING",
        "DRIPPERS",
        "DRIPPIER",
        "DRIPPILY",
        "DRIVABLE",
        "DRIVELED",
        "DRIVELER",
        "DRIZZLED",
        "DRIZZLES",
        "DRIVINGS",
        "DRIFTWOOD",
        "DRINKABLE",
        "DRIVELINE",
        "DRIPSTONE",
        "DRINKINGS",
        "DRIPPIEST",
        "DRIPPINGS",
        "DRIVEABLE",
        "DRIVELING",
        "DRIVELLED",
        "DRIVELLER",
        "DRIVELERS",
        "DRIVEWAYS",
        "DRIVINGLY",
        "DRIZZLING",
        "DRIZZLIER",
        "DRINKABLYDRILLABLE",
        "DRIFTPINS",
        "DRILLINGS",
        "DRIFTAGES",
        "DRIFTIEST",
        "DRIBBLETS",
        "DRIBBLING",
        "DRIBBLERS",
        "DRIVETRAIN",
        "DRIVESHAFT",
        "DRIVERLESS",
        "DRIVENNESS",
        "DRIVELLERS",
        "DRIVELLING",
        "DRIVELINES",
        "DRIZZLIEST",
        "DRIPSTONES",
        "DRIFTWOODS",
        "DRINKABLES",
        "DRIFTINGLY",
        "DRILLMASTER",
        "DRIVABILITY",
        "DRIVESHAFTS",
        "DRIVETRAINS",
        "DRIZZLINGLY",
        "DRINKABILITY",
        "DRILLMASTERS",
        "DRILLABILITY",
        "DRIVEABILITY",
        "DRIVENNESSES",
        "DRIVABILITIES",
        "DRINKABILITIES",
        "DRILLABILITIES",
        "DRIVEABILITIES",
        "APPLE",
        "APPLES",
        "APPLY",
        "APPLIED",
        "APPLIES",
        "APPLICATION",
        "APPLICATIONS",
        "DEVELOP",
        "DEVELOPS",
        "DEVELOPED",
        "DEVELOPER",
        "DEVELOPMENT",
        "DEVIANT",
        "OCCASIONAL",
        "OCCASIONALLY",
        "OCTOPUS",
        "OCTOPI",
        "OCTAGON",
        "OCCUPATION",
        "OCCUPATIONS",
        "MATE",
        "MATES",
        "MATING",
        "MATED",
        "MATURE",
        "MATURITY",
        "MATURITY"
    )

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

    private fun pauseTimer() {
        countDownTimer?.cancel()
        isTimeRunning = false
    }

    fun resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS
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
}