package com.dumi.ui.fragment.home


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dumi.R
import com.dumi.databinding.FragmentHomeBinding
import com.dumi.event.enums.Navigation
import com.dumi.event.eventtypes.ScreenNavigationEvent
import com.dumi.ui.activity.BaseActivity
import com.dumi.ui.fragment.BaseFragment

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
                    //Not used
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as BaseActivity<*, *>).isSupportActionBarVisible(true)
    }

    override fun onPause() {
        super.onPause()
        (activity as BaseActivity<*, *>).isSupportActionBarVisible(false)
    }
}
