package com.dumi.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("onEditorActionListener")
fun TextInputEditText.setOnEditorActionListener(listener: TextView.OnEditorActionListener) {
    setOnEditorActionListener(listener)
}