package com.github.talkbacktutorial.activities.modules.braillekeyboard

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentBrailleKeyboardModulePart1Binding


class BrailleKeyboardPart1Fragment: Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = BrailleKeyboardPart1Fragment()
    }

    private lateinit var binding: FragmentBrailleKeyboardModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private lateinit var editText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_braille_keyboard_module_part1, container, false)
        this.binding.brailleKeyboardConstraintLayout.visibility = View.INVISIBLE
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as BrailleKeyboardActivity)).onFinishedSpeaking(triggerOnce = true) {
            this.binding.brailleKeyboardConstraintLayout.visibility = View.VISIBLE
        }
        setupTextView()
        //this.binding.brailleKeyboardConstraintLayout.viewTreeObserver.addOnGlobalLayoutListener(keyboardLayoutListener)
        this.speakIntro()

    }

    private fun setupTextView(){
        this.editText = this.binding.editText
    }

    private fun speakIntro() {
        val intro = """
            Now you will learn how to open the braille keyboard from the virtual keyboard. Please 
            make sure that you have set-up the braille keyboard in Talkback settings prior to
            starting this module.
        """.trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }

    /** TODO: Implement Detection for Braille Keyboard
    private val keyboardLayoutListener: ViewTreeObserver.OnGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        // navigation bar height
        var navigationBarHeight = 0
        var resourceId: Int =
            resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        // status bar height
        var statusBarHeight = 0
        resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        // display window size for the app layout
        val rect = Rect()
        activity?.window?.decorView?.getWindowVisibleDisplayFrame(rect)
    }
    **/
}