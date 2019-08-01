package com.dumi.ui.fragment.endgame


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dumi.R
import com.dumi.databinding.FragmentEndGameBinding
import com.dumi.event.enums.Navigation
import com.dumi.event.eventtypes.ScreenNavigationEvent
import com.dumi.ui.activity.BaseActivity
import com.dumi.ui.fragment.BaseFragment
import com.dumi.ui.fragment.SharedGameVM
import kotlinx.android.synthetic.main.fragment_end_game.*

class EndGameFragment : BaseFragment<FragmentEndGameBinding, SharedGameVM>() {

    override val layoutId = R.layout.fragment_end_game
    override val viewModelClass = SharedGameVM::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(viewModelClass.java)
        } ?: throw Exception("Invalid Activity")

        (activity as BaseActivity<*, *>).hideKeyboard()

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.homeFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtFinalScore.text = viewModel.totalScore.value.toString()

        subscribe(ScreenNavigationEvent::class, Observer {
            when (it.navigation) {
                Navigation.QUIT_GAME -> {
                    findNavController().popBackStack(R.id.homeFragment, false)
                }
                else -> {
                    //Not used
                }
            }
        })
    }
}
