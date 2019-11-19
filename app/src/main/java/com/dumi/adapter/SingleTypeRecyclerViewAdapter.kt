package com.dumi.adapter

import androidx.databinding.ObservableArrayList
import com.dumi.ui.BaseViewModel

class SingleTypeRecyclerAdapter<T>(
    items: ObservableArrayList<T>,
    override var viewModel: BaseViewModel,
    val itemLayoutId: Int
) : BaseRecyclerViewAdapter<T>(items) {

    override fun getLayoutId(itemType: Int): Int {
        return itemLayoutId
    }
}