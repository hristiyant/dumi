package com.dumi.util

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

const val CYRILLIC_CHARACTERS_ONLY_REGEX = "[А-ЯA-Z]"

class RegexInputFilter : InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher = Pattern.compile(CYRILLIC_CHARACTERS_ONLY_REGEX).matcher(source)
        if (!matcher.matches()) {
            return ""
        }

        return null
    }
}