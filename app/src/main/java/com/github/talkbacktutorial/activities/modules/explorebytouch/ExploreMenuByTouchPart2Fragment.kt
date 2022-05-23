package com.github.talkbacktutorial.activities.modules.explorebytouch

import android.content.Context
import android.content.Intent
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
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.database.LessonProgressionViewModel
import com.github.talkbacktutorial.databinding.BasicCardBinding
import com.github.talkbacktutorial.databinding.FragmentExploreMenuByTouchModulePart2Binding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding

class ExploreMenuByTouchPart2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = ExploreMenuByTouchPart2Fragment()
    }

    private lateinit var binding: FragmentExploreMenuByTouchModulePart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private lateinit var lessonProgressionViewModel: LessonProgressionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore_menu_by_touch_module_part2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as ExploreMenuByTouchActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.showMenuItems(6)
                this.insertFinishLessonButton(6)
            }
        this.speakIntro()
    }

    /**
     * Generate three column menu - increase the difficulty
     * @author Jason Wu
     */
    private fun showMenuItems(amount: Int) {
        for (menuItemNum in 1..amount) {
            val basicCardBinding: BasicCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.basic_card, binding.layout1, false
            )
            basicCardBinding.text = "Row $menuItemNum Column1"
            basicCardBinding.card.setOnClickListener {
                ttsEngine.speak("You have interacted with a menu item. Find the button, then double tap to finish the lesson.")
            }
            binding.layout1.addView(basicCardBinding.card)
            if (menuItemNum == 1) basicCardBinding.card.sendAccessibilityEvent(
                AccessibilityEvent.TYPE_VIEW_FOCUSED
            )

            // Create the second column
            val basicCardBinding2: BasicCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.basic_card, binding.layout2, false
            )
            basicCardBinding2.text = "Row $menuItemNum Column2"
            basicCardBinding2.card.setOnClickListener {
                ttsEngine.speak("You have interacted with a menu item. Find the button, then double tap to finish the lesson.")
            }
            binding.layout2.addView(basicCardBinding2.card)

            // Create the third column
            val basicCardBinding3: BasicCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.basic_card, binding.layout3, false
            )
            basicCardBinding3.text = "Row $menuItemNum Column3"
            basicCardBinding3.card.setOnClickListener {
                ttsEngine.speak("You have interacted with a menu item. Find the button, then double tap to finish the lesson.")
            }
            binding.layout3.addView(basicCardBinding3.card)
        }
    }

    /**
     * Inserts a button to finish the lesson.
     * @author Jason Wu
     * @param position The position of the button among other menu items in the same container
     */
    private fun insertFinishLessonButton(position: Int) {
        val primaryButtonBinding: WidePillButtonBinding = DataBindingUtil.inflate(layoutInflater, R.layout.wide_pill_button, binding.layout2, false)
        primaryButtonBinding.text = "Finish Lesson"
        primaryButtonBinding.button.setOnClickListener {
            this.finishLesson()
        }
        binding.layout2.addView(primaryButtonBinding.button, position)
    }

    /**
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Jason Wu
     */
    private fun finishLesson() {

        lessonProgressionViewModel = ViewModelProvider(this).get(LessonProgressionViewModel::class.java)
        lessonProgressionViewModel.markModuleCompleted(context as Context)

        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent = Intent((activity as ExploreMenuByTouchActivity), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        this.ttsEngine.speak("You have completed the lesson. Sending you to the main menu.", override = true)
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jason Wu
     */
    private fun speakIntro() {
        val intro = """
            To finish this module, you will need to find the finish button in the menu items.
            Once you find the button, double tap to finish the module.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
