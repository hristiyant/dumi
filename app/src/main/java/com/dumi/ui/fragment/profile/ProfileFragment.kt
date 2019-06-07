package com.dumi.ui.fragment.profile


import com.dumi.R
import com.dumi.databinding.FragmentProfileBinding
import com.dumi.ui.fragment.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileVM>() {

    override val layoutId: Int = R.layout.fragment_profile
    override val viewModelClass = ProfileVM::class

    override fun isToolbarBackArrowVisible() = true
}
