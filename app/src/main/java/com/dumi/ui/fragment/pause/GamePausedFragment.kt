package com.dumi.ui.fragment.pause

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dumi.R
import com.dumi.databinding.DialogFragmentGamePausedBinding
import com.dumi.event.enums.Navigation
import com.dumi.event.eventtypes.ScreenNavigationEvent
import com.dumi.ui.activity.BaseActivity
import com.dumi.ui.fragment.BaseFragment


class GamePausedFragment :
    BaseFragment<DialogFragmentGamePausedBinding, GamePausedVM>() {

    override val layoutId = R.layout.dialog_fragment_game_paused

    override val viewModelClass = GamePausedVM::class

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as BaseActivity<*, *>).hideKeyboard()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe(ScreenNavigationEvent::class, Observer {
            when (it.navigation) {
                Navigation.RESUME -> {
                    findNavController().popBackStack(R.id.gameFragment, false)
                }
                Navigation.HELP -> {
                    findNavController().navigate(R.id.action_gamePausedFragment_to_helpFragment)
                }
                Navigation.QUIT_GAME -> {
                    findNavController().popBackStack(R.id.homeFragment, false)
                }
                Navigation.RESTART -> {
                    //use pop
                    findNavController().popBackStack(R.id.loadingGameFragment, false)
//                    findNavController().navigate(R.id.action_gamePausedFragment_to_loadingGameFragment)
                }
                else -> {
                    //Not used
                }
            }
        })
    }
}