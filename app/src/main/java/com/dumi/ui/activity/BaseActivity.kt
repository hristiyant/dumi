package com.dumi.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dumi.BR
import com.dumi.event.LiveEvent
import com.dumi.ui.BaseViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @get:LayoutRes
    protected abstract val layoutId: Int
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: KClass<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initViewModel()
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        initBinding(binding)
        setSupportActionBar(toolbar)
    }

    protected open fun initBinding(binding: B) {
        binding.setVariable(BR.vm, viewModel)
    }

    private fun initViewModel() {
        viewModel = viewModelFactory.create(viewModelClass.java)
        lifecycle.addObserver(viewModel)
    }

    protected fun <T : LiveEvent> subscribe(eventClass: KClass<T>, eventObserver: Observer<T>) {
        viewModel.subscribe(this, eventClass, eventObserver)
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

    fun isSupportActionBarVisible(isVisible: Boolean) {
        if (isVisible) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
    }
}