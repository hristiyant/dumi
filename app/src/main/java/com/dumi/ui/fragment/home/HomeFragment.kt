package com.dumi.ui.fragment.home


import android.os.Bundle
import android.view.View
import com.dumi.R
import com.dumi.databinding.FragmentHomeBinding
import com.dumi.ui.activity.main.MainActivity
import com.dumi.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeVM>() {

    override val layoutId: Int = R.layout.fragment_home
    override val viewModelClass = HomeVM::class

    override fun isToolbarBackArrowVisible() = true
}
