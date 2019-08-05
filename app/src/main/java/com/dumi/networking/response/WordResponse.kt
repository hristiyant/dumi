package com.dumi.networking.response

import com.google.gson.annotations.SerializedName

data class RepoResult(val words: List<WordResponse>)

data class WordResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("word")
    val word: String,
    @SerializedName("rootId")
    val rootId: Int
)