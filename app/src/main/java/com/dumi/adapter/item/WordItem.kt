package com.dumi.adapter.item

import androidx.lifecycle.LifecycleObserver
import com.dumi.R
import com.dumi.adapter.RecyclerItem

data class WordItem(
    var content: String? = "",
    var isCorrect: Boolean = false
) : LifecycleObserver, RecyclerItem {

    override fun getLayoutId(): Int {
        return if (isCorrect) {
            R.layout.item_correct_word
        } else {
            R.layout.item_wrong_word
        }
    }
}