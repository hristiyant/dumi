package com.dumi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.dumi.BR
import com.dumi.ui.BaseViewModel

abstract class BaseRecyclerViewAdapter<T>(var items: ObservableList<T>) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter<T>.BaseViewHolder<T, ViewDataBinding>>() {

    protected abstract var viewModel: BaseViewModel
    var lifecycleOwner: LifecycleOwner? = null

    @LayoutRes
    protected abstract fun getLayoutId(itemType: Int): Int

    private var onListChangedCallback =
        object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
            override fun onChanged(sender: ObservableList<T>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(
                sender: ObservableList<T>,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeMoved(
                sender: ObservableList<T>,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                notifyDataSetChanged()
            }

            override fun onItemRangeInserted(
                sender: ObservableList<T>,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeChanged(
                sender: ObservableList<T>,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeChanged(positionStart, itemCount)
            }
        }

    init {
        items.addOnListChangedCallback(onListChangedCallback)
    }

    private fun getViewHolderBinding(parent: ViewGroup, @LayoutRes itemLayoutId: Int): ViewDataBinding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), itemLayoutId, parent, false)

    @NonNull
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<T, ViewDataBinding> =
        object : BaseViewHolder<T, ViewDataBinding>(
            (getViewHolderBinding(
                parent,
                getLayoutId(viewType)
            )).root
        ) {}

    override fun onBindViewHolder(holder: BaseViewHolder<T, ViewDataBinding>, position: Int) {
        holder.bind(items[position], holder.binder)
        holder.binder?.executePendingBindings()
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: ObservableList<T>, vm: BaseViewModel) {
        this.items = items
        this.viewModel = vm
        items.addOnListChangedCallback(onListChangedCallback)
        notifyDataSetChanged()
    }

    abstract inner class BaseViewHolder<T, B : ViewDataBinding> constructor(view: View) :
        RecyclerView.ViewHolder(view) {

        val binder: B? = DataBindingUtil.bind(view)

        fun bind(item: T, binder: ViewDataBinding?) {
            binder?.setVariable(BR.vm, viewModel)
            binder?.setVariable(BR.item, item)
            if (lifecycleOwner != null) {
                binder?.lifecycleOwner = lifecycleOwner
            }
        }
    }

}