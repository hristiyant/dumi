package com.dumi.model

import java.util.HashSet

data class Level(
    val prefix: Prefix
)

data class Word(
    val wordId: Int,
    val word: String,
    val wordLength: Int
)

data class Prefix(
    val prefixID: Int,
    val prefix: String,
    val words: HashSet<Word>
)
