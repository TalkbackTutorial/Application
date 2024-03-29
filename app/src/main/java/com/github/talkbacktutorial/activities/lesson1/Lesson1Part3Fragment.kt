package com.github.talkbacktutorial.activities.lesson1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.BasicCardBinding
import com.github.talkbacktutorial.databinding.FragmentLesson1Part3Binding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding

class   Lesson1Part3Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = Lesson1Part3Fragment()
    }

    private lateinit var binding: FragmentLesson1Part3Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private lateinit var moduleProgressionViewModel: ModuleProgressionViewModel

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
            basicCardBinding.text = getString(R.string.menu_item) + " $menuItemNum"
            basicCardBinding.card.setOnClickListener {
                ttsEngine.speak(getString(R.string.lesson1_menu_item_prompt))
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
        primaryButtonBinding.text = getString(R.string.finish_lesson)
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
        moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }

        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            activity?.finish()
        }
        this.ttsEngine.speak(getString(R.string.lesson1_lesson_complete), override = true)
    }

    /**
     * Speaks an intro for the fragment.
     * @author Andre Pham
     */
    private fun speakIntro() {
        val intro = getString(R.string.lesson1_part3_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
