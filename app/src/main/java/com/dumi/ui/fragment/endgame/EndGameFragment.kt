package com.dumi.ui.fragment.endgame


import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.view.Display
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
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
import kotlinx.android.synthetic.main.fragment_game.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


class EndGameFragment : BaseFragment<FragmentEndGameBinding, SharedGameVM>() {

    override val layoutId = R.layout.fragment_end_game
    override val viewModelClass = SharedGameVM::class

    var txtFinalScoreHeight: Int = 0
    var txtFinalScoreWidth: Int = 0
    var txtFinalScoreX: Float = 0f
    var txtFinalScoreY: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSharedGameViewModel()
        addOnBackPressedCallback()
//        addTreeObserver()

        (activity as BaseActivity<*, *>).hideKeyboard()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addTreeObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtFinalScore.text = viewModel.totalScore.value.toString() //TODO: Use dataBinding.

        val handler = Handler()
        handler.postDelayed({
            viewConfetti.post {
                shootConfetti()
            }
        }, 5000)

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

    /**
     * Binds the viewModel to the activity instead of the fragment,
     * so that it can be shared with other fragments as well.
     */
    private fun initSharedGameViewModel() {
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(viewModelClass.java)
        } ?: throw Exception("Invalid Activity")
    }

    private fun addOnBackPressedCallback() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.homeFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun addTreeObserver() {
        val vto = container.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                container.viewTreeObserver
                    .removeOnGlobalLayoutListener(this)

                txtFinalScoreHeight = txtFinalScore.height
                txtFinalScoreWidth = txtFinalScore.width
                txtFinalScoreX = txtFinalScore.x
                txtFinalScoreY = txtFinalScore.y
            }
        })
    }

    private fun shootConfetti() {
        val display: Display = activity?.windowManager!!.defaultDisplay
        val size = Point()
        display.getSize(size)
        val x = size.x / 2
        val y = size.y / 2

        val cntxt = context ?: return

        //fall from top
        /*viewConfetti.build()
            .addColors(
                Color.WHITE,
                ContextCompat.getColor(cntxt, R.color.golden_word_already_mentioned)
            )
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes(Size(12), Size(16, 6f))
            .setPosition(-50f, viewConfetti.width + 50f, -50f, -50f)
            .streamFor(300, 5000)*/

        //burst from center
        viewConfetti.build()
            .addColors(
                Color.WHITE,
                ContextCompat.getColor(cntxt, R.color.golden_word_already_mentioned)
            )
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(800)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes(Size(6), Size(4, 6f))
            .setPosition(
                txtFinalScoreX - 10, txtFinalScoreX + txtFinalScoreWidth + 10,
                txtFinalScoreY - 10, txtFinalScoreX + txtFinalScoreHeight + 10
            )
            .burst(500)
    }
}
