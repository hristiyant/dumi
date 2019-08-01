package com.dumi.binding

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dumi.adapter.BaseRecyclerViewAdapter
import com.dumi.adapter.MultiTypeRecyclerAdapter
import com.dumi.adapter.RecyclerItem
import com.dumi.adapter.SingleTypeRecyclerAdapter
import com.dumi.ui.BaseViewModel

@BindingAdapter("viewModel", "items", "itemLayoutId", "orientation", "lifecycleOwner", "divider", requireAll = false)
fun <T> bindViewModel(view: RecyclerView,
                      vm: BaseViewModel?,
                      items: ObservableArrayList<T>,
                      itemLayoutId: Int?,
                      orientation: Int?,
                      lifecycleOwner : LifecycleOwner?,
                      dividerItemDecoration: RecyclerView.ItemDecoration?) {
    if (view.layoutManager == null) {
        view.layoutManager = LinearLayoutManager(view.context, orientation
            ?: RecyclerView.VERTICAL, false)
    }
    if (dividerItemDecoration != null) {
        view.addItemDecoration(dividerItemDecoration)
    }
    if (itemLayoutId != null) {
        view.adapter = SingleTypeRecyclerAdapter(items, vm, itemLayoutId)
    }
    if (view.adapter == null) {
        if (itemLayoutId != null) {
            view.adapter = SingleTypeRecyclerAdapter(items, vm, itemLayoutId)
        } else {
            view.adapter = MultiTypeRecyclerAdapter(items as ObservableArrayList<RecyclerItem>, vm)
        }
    } else {
        (view.adapter as BaseRecyclerViewAdapter<T>).setItems(items)
    }
    (view.adapter as BaseRecyclerViewAdapter<T>).lifecycleOwner = lifecycleOwner
}