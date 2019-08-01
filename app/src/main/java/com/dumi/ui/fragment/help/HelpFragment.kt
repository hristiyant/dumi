package com.dumi.ui.fragment.help

import com.dumi.R
import com.dumi.databinding.FragmentHelpBinding
import com.dumi.ui.fragment.BaseFragment


class HelpFragment : BaseFragment<FragmentHelpBinding, HelpVM>() {

    override val layoutId = R.layout.fragment_help

    override val viewModelClass = HelpVM::class

}
