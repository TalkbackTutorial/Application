package com.github.talkbacktutorial.activities.modules.virtualkeyboard

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentVirtualKeyboardModulePart1Binding

const val TYPED_STRING = "hello"

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
        setupTextView()
        this.binding.virtualKeyboardConstraintLayout.viewTreeObserver.addOnGlobalLayoutListener(keyboardLayoutListener)
        this.speakIntro()

    }

    private fun setupTextView(){
        this.editText = this.binding.editText
        editText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onFinishTyping()
            }
            false
        }
    }

    private fun onFinishTyping(){
        if (editText.text.toString().lowercase() == TYPED_STRING){
            val info = """Great job! You have typed the word hello with the on screen keyboard .
                    Congratulations on completing this lesson.
                    In this lesson you have successfully learnt how to type with the on screen keyboard.
                    To exit this lesson, select the finish button on the screen.""".trimIndent()
            speakDuringLesson(info)
            insertFinishButton()
        } else {
            val info = """Looks like you haven't typed the word hello. Double tap on the screen to bring up the keyboard.
                Tap on the backspace button to delete the previously typed word and try typing the word hello again.
            """.trimIndent()
            speakDuringLesson(info)
        }
    }

    private fun speakIntro() {
        val intro = """
            In this tutorial, you will be learning how to open the on-screen virtual keyboard and 
            type using it. To start, double tap on the screen to open the virtual keyboard. 
        """.trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }

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

        // screen height - (user app height + status + nav) ..... if non-zero, then there is a soft keyboard
        val keyboardHeight: Int = this.editText.height - (statusBarHeight + navigationBarHeight + rect.height())
        Log.i("KEYBOARD HEIGHT:", keyboardHeight.toString())
        if (keyboardHeight > 0) {
            onShowKeyboard()
        }
    }

    private fun onShowKeyboard() {
        if (firstTime) {
            val info = """Great job! You opened up the on screen virtual keyboard.
                    Explore by touch to type hello using the keyboard""".trimIndent()
            speakDuringLesson(info)
            firstTime = false
        }
    }
    private fun insertFinishButton() {
        val constraintLayout = this.binding.virtualKeyboardConstraintLayout
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.horizontalBias = 0.95f
        layoutParams.endToEnd = constraintLayout.id
        layoutParams.startToStart = constraintLayout.id
        layoutParams.topToTop = constraintLayout.id
        layoutParams.topMargin = 10.dpToPixels(requireContext())
        val finishButton = Button(requireContext())
        val text = "Finish"
        finishButton.contentDescription = text
        finishButton.text = text
        finishButton.layoutParams = layoutParams
        finishButton.setBackgroundResource(R.color.primary40)
        finishButton.setOnClickListener {
            endLesson()
        }

        constraintLayout.addView(finishButton)
    }

    private fun Int.dpToPixels(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

    private fun endLesson() {
        // Lesson's complete go back to Main Activity
        activity?.onBackPressed()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    private fun speakDuringLesson(info: String) {
        this.binding.virtualKeyboardConstraintLayout.visibility = View.GONE
        ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            this.binding.virtualKeyboardConstraintLayout.visibility = View.VISIBLE
        }
        ttsEngine.speak(info)
    }
}