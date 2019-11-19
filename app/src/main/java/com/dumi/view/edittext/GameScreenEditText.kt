package com.dumi.view.edittext

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

class GameScreenEditText : EditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private lateinit var listener: KeyImeChangeListener

    fun setKeyImeChangeListener(listener: KeyImeChangeListener) {
        this.listener = listener
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (listener != null) {
            listener.onKeyImeChange(keyCode, event)
        }
        return false
    }
}
