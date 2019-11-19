package com.dumi.adapter

import androidx.databinding.ObservableArrayList
import com.dumi.ui.BaseViewModel

class MultiTypeRecyclerAdapter<T : RecyclerItem>(
    items: ObservableArrayList<T>,
    override var viewModel: BaseViewModel
) : BaseRecyclerViewAdapter<T>(items) {

    override fun getLayoutId(itemType: Int): Int {
        return itemType
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].layoutId
    }
}