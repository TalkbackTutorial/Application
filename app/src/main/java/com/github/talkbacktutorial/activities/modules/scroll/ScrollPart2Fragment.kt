package com.github.talkbacktutorial.activities.modules.scroll

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.core.view.allViews
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.BasicHorizontalCardBinding
import com.github.talkbacktutorial.databinding.FragmentScrollingModulePart2Binding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding

class ScrollPart2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = ScrollPart2Fragment()
    }

    private lateinit var binding: FragmentScrollingModulePart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private val menuSize = 50

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scrolling_module_part2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as ScrollActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                binding.cardLinearLayout.visibility = View.VISIBLE
                binding.cardLinearLayout.allViews.first().sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
            }
        this.speakIntro()
        this.setupHorizontalScrollMenu(menuSize)
    }

    /**
     * Sets up the scroll menu's visibility and listener. Adds a button at the end to finish the lesson.
     * @param amount the number of menu items to show
     * @author Andre Pham
     */
    private fun setupHorizontalScrollMenu(amount: Int) {
        binding.cardLinearLayout.visibility = View.GONE
        for (menuItemNum in 1..amount) {
            val basicCardBinding: BasicHorizontalCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.basic_horizontal_card, binding.cardLinearLayout, false
            )
            basicCardBinding.text = "Menu Item $menuItemNum"
            basicCardBinding.card.setOnClickListener {
                val info = """
                    Menu Item $menuItemNum
                    To scroll right, swipe left with two fingers.
                    To scroll left, swipe with two fingers in the opposite direction.
                    To finish, first check the checkbox at the end of the horizontal menu, then press the button at the beginning of the horizontal menu.
                """.trimIndent()
                this.ttsEngine.speak(info)
            }
            binding.cardLinearLayout.addView(basicCardBinding.card)
        }
        val primaryButtonBinding: WidePillButtonBinding = DataBindingUtil.inflate(layoutInflater, R.layout.wide_pill_button, binding.cardLinearLayout, false)
        primaryButtonBinding.text = "Finish Lesson"
        primaryButtonBinding.button.setOnClickListener {
            this.finishLesson()
        }
        primaryButtonBinding.button.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        binding.cardLinearLayout.addView(primaryButtonBinding.button)
    }

    /**
     * Speaks an intro for the fragment.
     * @author Andre Pham
     */
    private fun speakIntro() {
        val intro = """
            Now you'll learn how to scroll through horizontal menus.
            When scrolling vertically, you used two fingers to swipe up and down.
            Now, when scrolling horizontally, you will use two fingers to swipe left and right.
            To scroll right, swipe left with two fingers.
            To scroll left, swipe with two fingers in the opposite direction.
            To finish the lesson, scroll to the end of the horizontal menu, and interact with the button to finish the lesson.
            You may now start.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Andre Pham
     */
    private fun finishLesson() {

        updateModule()

        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent = Intent((activity as ScrollActivity), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        this.ttsEngine.speak("You have completed the lesson. Sending you to the main menu.", override = true)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * This method updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule(){
        val moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }
}
