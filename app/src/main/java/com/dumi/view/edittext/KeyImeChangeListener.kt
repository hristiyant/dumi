package com.dumi.view.edittext

import android.view.KeyEvent

interface KeyImeChangeListener {
    fun onKeyImeChange(keyCode: Int, event: KeyEvent?)
}