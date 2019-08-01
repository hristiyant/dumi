package com.dumi.networking.repository

import com.dumi.networking.response.RepoResult
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Single

interface WordsRepository {

    @GET("words/{rootId}")
    fun getAllWordsByRootId(
        @Path("rootId") rootId: Int
    ): Single<RepoResult>
}