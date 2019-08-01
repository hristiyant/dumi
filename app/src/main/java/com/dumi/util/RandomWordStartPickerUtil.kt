package com.dumi.util

import java.util.*

object RandomWordStartPickerUtil {

    private var randomGenerator: Random = Random()
//    private var wordsList: ArrayList<String> = arrayListOf("СТО")
    private var wordsList: ArrayList<String> = arrayListOf("DRI","AP","DEV","OC","MA")

    fun getRandomWordStart(): String {
        val index = randomGenerator.nextInt(wordsList.size)
        //get dictionary for this index and save it if result is not an empty list
        return wordsList[index]
    }
}