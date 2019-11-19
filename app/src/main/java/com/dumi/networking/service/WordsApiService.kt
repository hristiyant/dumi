package com.dumi.networking.service

import com.dumi.model.Level
import com.dumi.model.Prefix
import com.dumi.networking.repository.WordsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordsApiService @Inject constructor(private val wordsApi: WordsApi) : BaseApiService() {

    suspend fun getLevels(): MutableList<Prefix>? {
        val gameResponse = safeApiCall(
            call = { wordsApi.getLevelsAsync() },
            errorMessage = "Error Fetching Game Levels' Data"
        )

        return gameResponse?.toMutableList()
    }
}