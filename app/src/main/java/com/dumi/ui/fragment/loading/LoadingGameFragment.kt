package com.dumi.ui.fragment.loading


import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dumi.R
import com.dumi.databinding.FragmentLoadingGameBinding
import com.dumi.ui.fragment.BaseGameFragment
import com.dumi.ui.fragment.SharedGameVM
import com.dumi.util.RandomWordStartPickerUtil
import kotlinx.android.synthetic.main.fragment_loading_game.*

class LoadingGameFragment : BaseGameFragment<FragmentLoadingGameBinding, SharedGameVM>() {

    var animationDrawable: AnimationDrawable? = null

    override val layoutId = R.layout.fragment_loading_game

    override val viewModelClass = SharedGameVM::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSharedGameVM()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animationDrawable = (imgCountdown.background) as AnimationDrawable

        viewModel.fetchGameLevels()
    }

    override fun onResume() {
        super.onResume()

        animationDrawable?.start()

        checkAnimationStatus(50, animationDrawable)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelAllRequests()
    }

    private fun initSharedGameVM() {
        activity?.let {
            viewModel = ViewModelProviders.of(it, viewModelFactory).get(viewModelClass.java)
        }
    }

    private fun generateAllGameLevels(): HashMap<Int, String> {
        var nextWordStart: String
        val map: HashMap<Int, String> = HashMap(3)
        var mapIndex = 0

        while (map.size < 3) {
            nextWordStart = RandomWordStartPickerUtil.getRandomWordStart()
            if (map.containsValue(nextWordStart)) {
                continue
            }
            map[mapIndex] = nextWordStart
            mapIndex = mapIndex.plus(1)
        }

        return map
    }

    private fun checkAnimationStatus(time: Int, animationDrawable: AnimationDrawable?) {
        val handler = Handler()
        handler.postDelayed({
            if (animationDrawable?.current != animationDrawable?.getFrame(animationDrawable.numberOfFrames - 1))
                checkAnimationStatus(time, animationDrawable)
            else findNavController().navigate(R.id.action_loadingGameFragment_to_gameFragment)
        }, time.toLong())
    }
}
