package com.dumi.networking.service

import com.dumi.networking.repository.WordsApi
import com.dumi.networking.response.RepoResult
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordsApiService @Inject constructor(private val wordsApi: WordsApi) {

    fun getAllWordsByRootId(rootId: Int): Single<RepoResult> {
        return wordsApi.getAllWordsByRootId(rootId)
    }
}