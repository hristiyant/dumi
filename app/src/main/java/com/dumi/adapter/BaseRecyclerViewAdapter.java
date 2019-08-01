package com.dumi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.dumi.BR;
import com.dumi.ui.BaseViewModel;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseMvvmRecyclerViewHolder> {

    protected BaseViewModel viewModel;
    protected ObservableList<T> items;
    private LifecycleOwner lifecycleOwner;

    private ObservableList.OnListChangedCallback<ObservableList<T>> onListChangedCallback;

    protected abstract @LayoutRes
    int getLayoutId(int itemType);

    public BaseRecyclerViewAdapter(ObservableList<T> items) {
        this.items = items;
        initOnListChangedListener();
    }

    public BaseRecyclerViewAdapter(ObservableList<T> items, BaseViewModel viewModel) {
        this.viewModel = viewModel;
        this.items = items;
        initOnListChangedListener();
    }

    private void initOnListChangedListener() {
        onListChangedCallback = new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override
            public void onChanged(ObservableList<T> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        };
        items.addOnListChangedCallback(onListChangedCallback);
    }

    private ViewDataBinding getViewHolderBinding(ViewGroup parent, @LayoutRes int itemLayoutId) {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), itemLayoutId, parent, false);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.BaseMvvmRecyclerViewHolder holder, int position) {
        T item = items.get(position);
        holder.bind(item, holder.binder);
        holder.binder.executePendingBindings();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(ObservableList<T> items) {
        this.items = items;
        initOnListChangedListener();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseMvvmRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UltimateViewHolder(getViewHolderBinding(parent, getLayoutId(viewType)));
    }

    public LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    protected abstract class BaseMvvmRecyclerViewHolder<VM, B extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private B binder;

        public BaseMvvmRecyclerViewHolder(View v) {
            super(v);
            binder = DataBindingUtil.bind(v);
        }

        protected void bind(final VM item, final B binder) {
            binder.setVariable(com.dumi.BR.vm, viewModel);
            binder.setVariable(BR.item, item);
            if (lifecycleOwner != null) {
                binder.setLifecycleOwner(lifecycleOwner);
            }
        }

    }

    private class UltimateViewHolder extends BaseMvvmRecyclerViewHolder<Object, ViewDataBinding> {
        UltimateViewHolder(ViewDataBinding b) {
            super(b.getRoot());
        }
    }

}