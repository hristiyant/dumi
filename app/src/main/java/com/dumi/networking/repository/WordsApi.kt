package com.dumi.networking.repository

import com.dumi.model.Level
import com.dumi.model.Prefix
import retrofit2.Response
import retrofit2.http.GET

interface WordsApi {

    @GET("game/load-levels")
    suspend fun getLevelsAsync(): Response<List<Prefix>>
}