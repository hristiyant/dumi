package com.dumi.ui.fragment.game


import com.dumi.R
import com.dumi.databinding.FragmentGameBinding
import com.dumi.ui.fragment.BaseFragment

class GameFragment : BaseFragment<FragmentGameBinding, GameVM>() {

    override val layoutId: Int = R.layout.fragment_game
    override val viewModelClass = GameVM::class

    override fun isToolbarBackArrowVisible() = true
}
