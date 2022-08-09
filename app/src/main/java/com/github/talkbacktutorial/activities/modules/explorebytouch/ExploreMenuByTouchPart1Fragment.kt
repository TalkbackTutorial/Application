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
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.LessonActivity
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.BasicCardBinding
import com.github.talkbacktutorial.databinding.FragmentExploreMenuByTouchModulePart1Binding
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer

class ExploreMenuByTouchPart1Fragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = ExploreMenuByTouchPart1Fragment()
    }
    private lateinit var binding: FragmentExploreMenuByTouchModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore_menu_by_touch_module_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as ExploreMenuByTouchActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.showMenuItems(7)
            }
        this.speakIntro()
    }

    /**
     * Generate three column menu - allow explore in both vertical and horizontal
     * @author Jason Wu
     */
    private fun showMenuItems(amount: Int) {
        for (menuItemNum in 1..amount) {
            val basicCardBinding: BasicCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.basic_card, binding.layout1, false
            )
            basicCardBinding.text = getString(R.string.row) + "$menuItemNum " + getString(R.string.column1)
            basicCardBinding.card.setOnClickListener {
                ttsEngine.speak(getString(R.string.explore_menu_by_touch_menu_item_prompt))
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
            if(menuItemNum == 6){
                basicCardBinding2.text = getString(R.string.finish_lesson_button)
                basicCardBinding2.card.setOnClickListener{
                    this.finishLesson()
                }
            }
            else{
                basicCardBinding2.text = getString(R.string.row) + "$menuItemNum " + getString(R.string.column2)
                basicCardBinding2.card.setOnClickListener {
                    ttsEngine.speak(getString(R.string.explore_menu_by_touch_menu_item_prompt))
                }
            }
            binding.layout2.addView(basicCardBinding2.card)

            // Create the third column
            val basicCardBinding3: BasicCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.basic_card, binding.layout3, false
            )
            basicCardBinding3.text = getString(R.string.row) + "$menuItemNum " + getString(R.string.column3)
            basicCardBinding3.card.setOnClickListener {
                ttsEngine.speak(getString(R.string.explore_menu_by_touch_menu_item_prompt))
            }
            binding.layout3.addView(basicCardBinding3.card)
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jason Wu
     */
    private fun speakIntro() {
        val intro = getString(R.string.explore_menu_by_touch_part1_intro).trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Jason Wu
     */
    private fun finishLesson() {

        updateModule()

        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            activity?.finish()
        }
        this.ttsEngine.speak(getString(R.string.explore_menu_by_touch_part2_outro), override = true)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * Updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule(){
        val moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }
}
