package com.dumi.util

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

/**
 * This class is used to filter the user's input from the keyboard one symbol by symbol
 * in order to prevent the user from entering anything other than the symbols allowed in the regex.
 * Since the game is about entering words in a selected language, we only need to allow input of
 * letters from the corresponding alphabet.
 */
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
        // TODO: 7.10.2019 Try to adjust without nullable types
        return null
    }
}