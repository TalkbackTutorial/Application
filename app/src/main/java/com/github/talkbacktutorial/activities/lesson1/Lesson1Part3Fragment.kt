package com.github.talkbacktutorial.activities.lesson1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.database.LessonProgressionViewModel
import com.github.talkbacktutorial.databinding.BasicCardBinding
import com.github.talkbacktutorial.databinding.FragmentLesson1Part3Binding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding

class Lesson1Part3Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = Lesson1Part3Fragment()
    }

    private lateinit var binding: FragmentLesson1Part3Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson1_part3, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as Lesson1Activity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.showMenuItems(6)
                this.insertFinishLessonButton(4)
            }
        this.speakIntro()
    }

    /**
     * Displays a list of menu items that the user can interact with for feedback.
     * @author Andre Pham
     * @param amount The number of menu items to show
     */
    private fun showMenuItems(amount: Int) {
        for (menuItemNum in 1..amount) {
            val basicCardBinding: BasicCardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.basic_card, binding.linearLayout, false)
            basicCardBinding.text = "Menu Item $menuItemNum"
            basicCardBinding.card.setOnClickListener {
                ttsEngine.speak("You have interacted with a menu item. Find the button, then double tap to finish the lesson.")
            }
            binding.linearLayout.addView(basicCardBinding.card)
            if (menuItemNum == 1) basicCardBinding.card.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
        }
    }

    /**
     * Inserts a button to finish the lesson.
     * @author Andre Pham
     * @param position The position of the button among other menu items in the same container
     */
    private fun insertFinishLessonButton(position: Int) {
        val primaryButtonBinding: WidePillButtonBinding = DataBindingUtil.inflate(layoutInflater, R.layout.wide_pill_button, binding.linearLayout, false)
        primaryButtonBinding.text = "Finish Lesson"
        primaryButtonBinding.button.setOnClickListener {
            this.finishLesson()
        }
        binding.linearLayout.addView(primaryButtonBinding.button, position)
    }

    /**
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Andre Pham
     */
    private fun finishLesson() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            LessonProgressionViewModel.getProgressionViewModel(context as Context).updateCompletedModules(context as Context)
            LessonProgressionViewModel.getProgressionViewModel(context as Context).markLessonCompleted(context as Context)

            val intent = Intent((activity as Lesson1Activity), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        this.ttsEngine.speak("You have completed the lesson. Sending you to the main menu.", override = true)
    }

    /**
     * Speaks an intro for the fragment.
     * @author Andre Pham
     */
    private fun speakIntro() {
        val intro = """
            You will now learn to interact with elements on the screen.
            To interact with an element, double tap when you have selected it.
            For this activity, explore the elements on the screen until you reach the button.
            To tap the button and finish the lesson, double tap.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
