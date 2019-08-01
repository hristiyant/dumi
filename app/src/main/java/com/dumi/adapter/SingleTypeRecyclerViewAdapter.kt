package com.dumi.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableArrayList
import com.dumi.ui.BaseViewModel

class SingleTypeRecyclerAdapter<T> : BaseRecyclerViewAdapter<T> {

    @LayoutRes
    private var layoutId: Int = 0

    constructor(
        items: ObservableArrayList<T>,
        viewModel: BaseViewModel?,
        itemLaoyutId: Int
    ) : super(items, viewModel) {
        this.layoutId = itemLaoyutId
    }

    constructor(items: ObservableArrayList<T>, itemLaoyutId: Int) : super(items) {
        this.layoutId = itemLaoyutId
    }

    override fun getLayoutId(itemType: Int): Int {
        return layoutId
    }
}