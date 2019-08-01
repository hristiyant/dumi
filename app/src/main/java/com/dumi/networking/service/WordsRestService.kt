package com.dumi.networking.service

import com.dumi.networking.repository.WordsRepository
import com.dumi.networking.response.RepoResult
import rx.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordsRestService @Inject constructor(private val wordsRepository: WordsRepository) {

    fun getAllWordsByRootId(rootId: Int): Single<RepoResult> {
        return wordsRepository.getAllWordsByRootId(rootId)
    }
}