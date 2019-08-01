package com.dumi.adapter

import androidx.databinding.ObservableArrayList
import com.dumi.ui.BaseViewModel

class MultiTypeRecyclerAdapter<T : RecyclerItem> : BaseRecyclerViewAdapter<T> {

    constructor(items: ObservableArrayList<T>, vm: BaseViewModel?) : super(items, vm)

    constructor(items: ObservableArrayList<T>) : super(items)

    override fun getLayoutId(itemType: Int): Int {
        return itemType
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId
    }
}