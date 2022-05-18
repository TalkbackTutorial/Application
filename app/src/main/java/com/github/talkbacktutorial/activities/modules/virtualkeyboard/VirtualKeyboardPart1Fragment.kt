package com.github.talkbacktutorial.activities.modules.virtualkeyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentVirtualKeyboardModulePart1Binding
import android.graphics.Rect

import android.view.ViewTreeObserver




class VirtualKeyboardPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = VirtualKeyboardPart1Fragment()
    }

    private lateinit var binding: FragmentVirtualKeyboardModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_virtual_keyboard_module_part1, container, false)
        this.binding.virtualKeyboardConstraintLayout.visibility = View.INVISIBLE
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as VirtualKeyboardActivity)).onFinishedSpeaking(triggerOnce = true) {
            this.binding.virtualKeyboardConstraintLayout.visibility = View.VISIBLE
        }
        this.speakIntro()
    }

    private fun speakIntro() {
        val intro = """
            In this tutorial, you will be learning how to open the on-screen virtual keyboard and 
            type using it. To start, explore by touch to find the text box then double tap to open
            the virtual keyboard. 
        """.trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }

    private val keyboardLayoutListener: OnGlobalLayoutListener = object : OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            // navigation bar height
            var navigationBarHeight = 0
            var resourceId: Int =
                getResources().getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                navigationBarHeight = getResources().getDimensionPixelSize(resourceId)
            }

            // status bar height
            var statusBarHeight = 0
            resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId)
            }

            // display window size for the app layout
            val rect = Rect()
            getWindow().getDecorView().getWindowVisibleDisplayFrame(rect)

            // screen height - (user app height + status + nav) ..... if non-zero, then there is a soft keyboard
            val keyboardHeight: Int = rootLayout.getRootView()
                .getHeight() - (statusBarHeight + navigationBarHeight + rect.height())
            if (keyboardHeight <= 0) {
                //onHideKeyboard()
            } else {
                val info = """Great job! You opened up the on screen virtual keyboard.
                    Explore by touch to type hello using the keyboard""".trimIndent()
                ttsEngine.speak(info)

            }
        }
    }
}