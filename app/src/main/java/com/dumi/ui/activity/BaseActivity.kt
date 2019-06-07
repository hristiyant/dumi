package com.dumi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.dumi.ui.BaseViewModel

abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {


}