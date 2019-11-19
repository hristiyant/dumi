package com.dumi.ui.fragment.game


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.InputFilter
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dumi.R
import com.dumi.adapter.item.WordItem
import com.dumi.databinding.FragmentGameBinding
import com.dumi.event.enums.InGame
import com.dumi.event.enums.Navigation
import com.dumi.event.eventtypes.DisplayWordEvent
import com.dumi.event.eventtypes.InGameEvent
import com.dumi.event.eventtypes.ScreenNavigationEvent
import com.dumi.extension.hide
import com.dumi.ui.activity.BaseActivity
import com.dumi.ui.fragment.BaseGameFragment
import com.dumi.ui.fragment.SharedGameVM
import com.dumi.util.RegexInputFilter
import com.dumi.view.edittext.KeyImeChangeListener
import kotlinx.android.synthetic.main.fragment_game.*


class GameFragment : BaseGameFragment<FragmentGameBinding, SharedGameVM>(),
    TextView.OnEditorActionListener,
    View.OnFocusChangeListener, KeyImeChangeListener {

    private val cyrillicFilter = RegexInputFilter()

    override val layoutId: Int = R.layout.fragment_game
    override val viewModelClass = SharedGameVM::class

    override fun isToolbarBackArrowVisible() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSharedGameViewModel()

        //Init events subscriptions for this screen.
        subscribeForScreenNavigationEvent()
        subscribeForDisplayWordEvent()
        subscribeForInGameEvent()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)

//        binding.fragment = this

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        initEditTextView()
        viewModel.startTimer()
    }

    override fun onResume() {
        super.onResume()

        txtInput.requestFocus()
        /*KeyboardEventListener(activity as MainActivity) { isOpen ->
            if (isOpen) viewModel.startTimer()
        }*/
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
//            addAnimation()
            viewModel.checkWord(txtWordStart.text.toString() + v?.text.toString())
            return true
        }
        return false
    }

    /**
     * Binds the viewModel to the activity instead of the fragment,
     * so that it can be shared with other fragments as well.
     */
    private fun initSharedGameViewModel() {
        viewModel = activity?.let {
            ViewModelProviders.of(it, viewModelFactory).get(viewModelClass.java)
        } ?: throw Exception("Invalid Activity")
    }

    private fun startEnterAnimations() {
        setVisibility(View.VISIBLE, txtTimeLeft, btnPause, txtWordStart, txtInput)
        txtTimeLeft.run {
            startAnimation(initAnimation(this, R.anim.in_top, View.VISIBLE))
        }
        txtInput.text.clear()
        txtInput.requestFocus()
        viewModel.resetTimer()
        viewModel.startTimer()
        setStartWord()
    }

    private fun setVisibility(visibility: Int, vararg views: View) {
        for (view in views) {
            view.visibility = visibility
        }
    }

    private fun initEditTextView() {
        //Force all caps input and allow only Cyrillic characters.
        txtInput.filters =
            txtInput.filters + InputFilter.AllCaps() + cyrillicFilter
        txtInput.onFocusChangeListener = this
        txtInput.setKeyImeChangeListener(this)
        setStartWord()
    }

    //Adds animation to the EditText input
    /*private fun addAnimation() {
        val anim = AnimationUtils.loadAnimation(context, R.anim.out_bottom)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
//                onSubmitWord()
            }

            override fun onAnimationStart(animation: Animation?) {
                txtInput.text?.clear()
            }
        })
        txtInput.startAnimation(anim)
    }*/

    private fun showWord(word: WordItem) {
//        binding.setVariable(BR.item, word)
        viewModel.dumi.add(word)
        viewModel.allEntries.add(word.content)
        recyclerView.smoothScrollToPosition(viewModel.dumi.size - 1)
        txtInput.text?.clear()
        txtAlreadyMentionedWarning.visibility = View.GONE
    }

    private fun showAlreadyMentioned() {
        val anim = AnimationUtils.loadAnimation(context, R.anim.shake)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                //Not used.
            }

            override fun onAnimationEnd(animation: Animation?) {
                val handler = Handler()
                handler.postDelayed({
                    txtAlreadyMentionedWarning.visibility = View.GONE
                }, 300)
            }

            override fun onAnimationStart(animation: Animation?) {
                txtAlreadyMentionedWarning.visibility = View.VISIBLE
            }
        })
        anim.reset()
        txtAlreadyMentionedWarning.clearAnimation()
        txtAlreadyMentionedWarning.startAnimation(anim)
    }

    private fun setStartWord() {
        txtWordStart.text =
            viewModel.gameLevelsLiveData.value?.get(viewModel.levelsCounter)?.prefix
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (hasFocus) {
            if (view != null) {
                (activity as BaseActivity<*, *>).showKeyboard(view)
            }
        }
    }

    override fun onKeyImeChange(keyCode: Int, event: KeyEvent?) {
        if (KeyEvent.KEYCODE_BACK == keyCode && event!!.action == KeyEvent.ACTION_DOWN) {
            viewModel.onPauseClick()
        }
    }

    private fun startExitAnimations() {
        txtTimeLeft.run {
            startAnimation(initAnimation(this, R.anim.out_top, View.INVISIBLE))
        }
        btnPause.run {
            startAnimation(initAnimation(this, R.anim.out_start, View.INVISIBLE))
        }
        txtWordStart.run {
            startAnimation(initAnimation(this, R.anim.out_start, View.INVISIBLE))
        }
        txtInput.hide()
//        recyclerView.clear - viewModel.dumi.clear()
    }

    private fun initAnimation(view: View, animId: Int, finalViewState: Int): Animation {
        val anim = AnimationUtils.loadAnimation(context, animId)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {} // Not used

            override fun onAnimationEnd(animation: Animation?) {
                view.visibility = finalViewState
            }

            override fun onAnimationStart(animation: Animation?) {} // Not used
        })
        return anim
    }

    private fun subscribeForScreenNavigationEvent() {
        subscribe(ScreenNavigationEvent::class, Observer {
            when (it.navigation) {
                Navigation.GAME_PAUSED -> {
                    findNavController().navigate(R.id.action_gameFragment_to_gamePausedDialogFragment)
                }
                Navigation.NEXT_LEVEL -> {
                    viewModel.dumi.clear()
                    startExitAnimations()
                    val handler = Handler()
                    handler.postDelayed({
                        startEnterAnimations()
                    }, 2000)
                }
                Navigation.END_GAME -> {
                    val handler = Handler()
                    handler.postDelayed({
                        txtInput.hideKeyboard()
                    }, 5000)
                    findNavController().navigate(R.id.action_gameFragment_to_endGameFragment)
                }
                else -> {
                    //Not used
                }
            }
        })
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun subscribeForDisplayWordEvent() {
        subscribe(DisplayWordEvent::class, Observer { showWord(it.word) })
    }

    private fun subscribeForInGameEvent() {
        subscribe(InGameEvent::class, Observer {
            when (it.inGameEnum) {
                InGame.ALREADY_MENTIONED -> showAlreadyMentioned()
            }
        })
    }
}