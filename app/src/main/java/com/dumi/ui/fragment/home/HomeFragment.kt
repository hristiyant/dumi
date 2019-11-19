package com.dumi.ui.fragment.home

import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dumi.R
import com.dumi.databinding.FragmentHomeBinding
import com.dumi.event.enums.Navigation
import com.dumi.event.eventtypes.ScreenNavigationEvent
import com.dumi.ui.activity.BaseActivity
import com.dumi.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeVM>() {

    override val layoutId: Int = R.layout.fragment_home
    override val viewModelClass = HomeVM::class

    override fun isToolbarBackArrowVisible() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe(ScreenNavigationEvent::class, Observer {
            when (it.navigation) {
                Navigation.START_GAME -> {
                    findNavController().navigate(R.id.action_homeFragment_to_loadingGameFragment)
                }
                else -> {
                    // TODO: Not used.
                }
            }
        })

        btnKonfetti.setOnClickListener {
            shootConfetti()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as BaseActivity<*, *>).isSupportActionBarVisible(true)
    }

    override fun onPause() {
        super.onPause()
        (activity as BaseActivity<*, *>).isSupportActionBarVisible(false)
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
            .setTimeToLive(1000)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes(Size(6), Size(4, 6f))
            .setPosition(
                btnStart.x - 10, btnStart.x + btnStart.width + 10,
                btnStart.y - 10, btnStart.y + btnStart.height + 10
            )
            .burst(500)
    }
}
