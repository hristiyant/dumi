package com.dumi.ui.fragment.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dumi.R
import com.dumi.databinding.FragmentProfileBinding
import com.dumi.ui.fragment.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileVM>() {

    override val layoutId: Int = R.layout.fragment_profile
    override val viewModelClass = ProfileVM::class

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

}
