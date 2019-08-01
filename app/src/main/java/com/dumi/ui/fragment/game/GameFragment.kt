package com.dumi.ui.fragment.game


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
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dumi.BR
import com.dumi.R
import com.dumi.adapter.item.WordItem
import com.dumi.databinding.FragmentGameBinding
import com.dumi.event.enums.Navigation
import com.dumi.event.eventtypes.ScreenNavigationEvent
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
    private var shortAnimationDuration: Int = 0

    override val layoutId: Int = R.layout.fragment_game
    override val viewModelClass = SharedGameVM::class

    override fun isToolbarBackArrowVisible() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(viewModelClass.java)
        } ?: throw Exception("Invalid Activity")

        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        viewModel.resetGameVariables()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)

        binding.fragment = this

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEditTextView()
        viewModel.startTimer()

        subscribe(ScreenNavigationEvent::class, Observer {
            when (it.navigation) {
                Navigation.GAME_PAUSED -> {
                    findNavController().navigate(R.id.action_gameFragment_to_gamePausedDialogFragment)
                }
                Navigation.NEXT_LEVEL -> {
                    startExitAnimations()
                    val handler = Handler()
                    handler.postDelayed({
                        startEnterAnimations()
                    }, 2000)
                }
                Navigation.END_GAME -> {
                    findNavController().navigate(R.id.action_gameFragment_to_endGameFragment)
                }
                else -> {
                    //Not used
                }
            }
        })

        viewModel.timeLeftFormatted.observe(viewLifecycleOwner, Observer { time ->
            txtTimeLeft.text = time
        })
    }

    private fun startEnterAnimations() {
        setVisibility(View.VISIBLE, txtTimeLeft, btnPause, txtWordStart, txtInput)
        txtTimeLeft.run {
            startAnimation(initAnimation(this, R.anim.in_top, View.VISIBLE))
        }
        txtInput.requestFocus()
        viewModel.resetTimer()
        viewModel.startTimer()
        setStartWord()
    }

    override fun onResume() {
        super.onResume()

        txtInput.requestFocus()
    }

    private fun initEditTextView() {
        //Force all caps input and allow only Cyrillic characters.
        txtInput.filters =
            txtInput.filters + InputFilter.AllCaps() + cyrillicFilter
        txtInput.onFocusChangeListener = this
        txtInput.setKeyImeChangeListener(this)
        setStartWord()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
//            addAnimation()
            checkIsWordCorrect(txtWordStart.text.toString() + v?.text.toString())
            return true
        }
        return false
    }

    //Adds animation to the EditText input
    /*private fun addAnimation() {
        val anim = AnimationUtils.loadAnimation(context, R.anim.out_bottom)
        *//*anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
//                onSubmitWord()
            }

            override fun onAnimationStart(animation: Animation?) {
                txtInput.text?.clear()
            }
        })*//*
        txtInput.startAnimation(anim)
    }*/

    private fun checkIsWordCorrect(word: String) {
        val newWordAsObject: WordItem

        if (!viewModel.allEntries.contains(word)) {
            if (viewModel.dictionaryBulgarian.contains(word)) {

                //valid word print it on screen with white font
                newWordAsObject = WordItem(word, true)
                showCorrectWord(newWordAsObject)
                viewModel.increaseTotalScore(120)

            } else {

                //NOT valid - print on screen with red font
                newWordAsObject = WordItem(word, false)
                showCorrectWord(newWordAsObject)
            }
        } else {

            //show txtAlreadyMentionedWarning
            shakeAnimation()
        }

        recyclerView.smoothScrollToPosition(viewModel.dumi.size - 1)
        txtInput.text?.clear()
        txtAlreadyMentionedWarning.visibility = View.GONE
    }

    private fun showCorrectWord(word: WordItem) {
        binding.setVariable(BR.item, word)
        viewModel.dumi.add(word)
        viewModel.allEntries.add(word.content)
    }

    private fun setStartWord() {
        txtWordStart.text =
            viewModel.startWordsMap.value?.get(viewModel.levelsCounter)
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

    private fun shakeAnimation() {
//        txtAlreadyMentionedWarning.visibility = View.VISIBLE
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
        txtInput.visibility = View.INVISIBLE
//        recyclerView.clear
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

    private fun setVisibility(visibility: Int, vararg views: View) {
        for (view in views) {
            view.visibility = visibility
        }
    }
}