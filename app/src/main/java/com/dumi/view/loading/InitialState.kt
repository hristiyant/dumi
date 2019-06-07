package com.dumi.view.loading

import java.io.Serializable

sealed class LoadingState : Serializable

object Normal : LoadingState()
object Loading : LoadingState()
object Empty : LoadingState()
data class Error(val throwable: Throwable) : LoadingState()

enum class InitialState {
    Normal,
    Loading,
    Empty
}