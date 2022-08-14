package com.github.talkbacktutorial.activities.modules.virtualkeyboard

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.accessibility.AccessibilityEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentVirtualKeyboardModulePart1Binding


class VirtualKeyboardPart1Fragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = VirtualKeyboardPart1Fragment()
    }

    private lateinit var binding: FragmentVirtualKeyboardModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private lateinit var editText: EditText
    private var firstTime: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_virtual_keyboard_module_part1, container, false)
        this.binding.virtualKeyboardConstraintLayout.visibility = View.INVISIBLE
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as VirtualKeyboardActivity)).onFinishedSpeaking(triggerOnce = true) {
            this.binding.virtualKeyboardConstraintLayout.visibility = View.VISIBLE
        }
        setupTextView()
        this.binding.virtualKeyboardConstraintLayout.viewTreeObserver.addOnGlobalLayoutListener(keyboardLayoutListener)
        this.speakIntro()

    }

    /**
     * Function that setups a EditText field which can be used to type in and calls the function
     * onFinishTyping() when the user has finished typing.
     * @author Sandy Du
     */
    private fun setupTextView() {
        this.editText = this.binding.editText
        editText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onFinishTyping()
            }
            false
        }
    }

    /**
     * Function that checks if the user has typed in the correct word requested in the text field
     * @author Sandy Du
     */
    private fun onFinishTyping() {
        if (editText.text.toString().lowercase() == getString(R.string.virtual_keyboard_hello)){
            val info = getString(R.string.virtual_keyboard_part1_on_finish_typing).trimIndent()
            speakDuringLesson(info)
            ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                endLesson()
            }

        } else {
            val info = getString(R.string.virtual_keyboard_part1_on_finish_typing_fail).trimIndent()
            speakDuringLesson(info)
        }
    }

    /**
     * Function that uses the TTS Engine to speak the intro when the module is first opened.
     * @author Sandy Du, Nabeeb Yusuf
     */
    private fun speakIntro() {
        val intro = getString(R.string.virtual_keyboard_part1_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Function that observes the change in dimension such as height of the screen to determine if
     * the virtual keyboard has been opened. If it has been opened, it calls the function
     * onShowKeyboard()
     * @author Sandy Du, Nabeeb Yusuf
     */
    private val keyboardLayoutListener: ViewTreeObserver.OnGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        // navigation bar height
        var navigationBarHeight = 0
        var resourceId: Int =
            resources.getIdentifier(getString(R.string.virtual_keyboard_navigation_bar_height), getString(R.string.virtual_keyboard_dimen), getString(R.string.android))
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        // status bar height
        var statusBarHeight = 0
        resourceId = resources.getIdentifier(getString(R.string.virtual_keyboard_status_bar_height), getString(R.string.virtual_keyboard_dimen), getString(R.string.android))
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        // display window size for the app layout
        val rect = Rect()
        activity?.window?.decorView?.getWindowVisibleDisplayFrame(rect)

        // screen height - (user app height + status + nav) ..... if non-zero, then there is a soft keyboard
        val keyboardHeight: Int = this.editText.height - (statusBarHeight + navigationBarHeight + rect.height())
        if (keyboardHeight > 0) {
            onShowKeyboard()
        }
    }

    /**
     * Function that uses the TTS Engine to give audio feedback when the virtual keyboard has
     * been opened.
     * @author Sandy Du
     */
    private fun onShowKeyboard() {
        if (firstTime) {
            val info = getString(R.string.virtual_keyboard_part1_on_show_keyboard).trimIndent()
            this.binding.virtualKeyboardConstraintLayout.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
            speakDuringLessonKeyboard(info)
            firstTime = false
        }
    }

    /**
     * Function that exits the module.
     * @author Sandy Du
     */
    private fun endLesson() {
        // Lesson's complete go back to Main Activity
        activity?.finish()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * Function that hides the screen when the tts engine is speaking
     * @author Team4
     */
    private fun speakDuringLesson(info: String) {
        this.binding.virtualKeyboardConstraintLayout.visibility = View.GONE
        ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            this.binding.virtualKeyboardConstraintLayout.visibility = View.VISIBLE
        }
        Handler().postDelayed({
            ttsEngine.speak(info)
        },4000)
    }

    /**
     * Function that hides the screen when the tts engine is speaking and the keyboard is shown
     * @author Team4
     */
    private fun speakDuringLessonKeyboard(info: String) {
        this.binding.virtualKeyboardConstraintLayout.visibility = View.GONE
        Handler().postDelayed({
            ttsEngine.speak(info)
        }, 10000)
        ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            this.binding.virtualKeyboardConstraintLayout.visibility = View.VISIBLE
        }
    }
}