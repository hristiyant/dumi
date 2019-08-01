package com.dumi.ui.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dumi.BR
import com.dumi.event.LiveEvent
import com.dumi.ui.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseDialogFragment<B : ViewDataBinding, VM : BaseViewModel> :
    DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: KClass<VM>

    protected open fun initBinding(binding: B) {
        binding.setVariable(BR.vm, viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass.java)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initBinding(binding)
        return binding.root
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    protected fun <T : LiveEvent> subscribe(eventClass: KClass<T>, eventObserver: Observer<T>) {
        viewModel.subscribe(this, eventClass, eventObserver)
    }
}