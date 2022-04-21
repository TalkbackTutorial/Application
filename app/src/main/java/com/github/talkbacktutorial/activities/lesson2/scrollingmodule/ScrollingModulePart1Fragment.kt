package com.github.talkbacktutorial.activities.lesson2.scrollingmodule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.core.view.allViews
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.lesson0.Lesson0Activity
import com.github.talkbacktutorial.activities.lesson0.Lesson0Part2Fragment
import com.github.talkbacktutorial.activities.lesson0.Lesson0Part3Fragment
import com.github.talkbacktutorial.activities.modules.ScrollingModuleActivity
import com.github.talkbacktutorial.databinding.BasicCardBinding
import com.github.talkbacktutorial.databinding.FragmentLesson0Part1Binding
import com.github.talkbacktutorial.databinding.FragmentScrollingModulePart1Binding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding

class ScrollingModulePart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = ScrollingModulePart1Fragment()
    }

    private lateinit var binding: FragmentScrollingModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private val menuSize = 50

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scrolling_module_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as ScrollingModuleActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                binding.cardLinearLayout.visibility = View.VISIBLE
                binding.cardLinearLayout.allViews.first().sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
            }
        this.setupVerticalScrollMenu(this.menuSize)
        this.speakIntro()
    }

    /**
     * Sets up the scroll menu's visibility and listener. Adds a Continue button at the end.
     * @param amount the number of menu items to show
     * @author Andre Pham
     */
    private fun setupVerticalScrollMenu(amount: Int) {
        binding.cardLinearLayout.visibility = View.GONE
        for (menuItemNum in 1..amount) {
            val basicCardBinding: BasicCardBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.basic_card, binding.cardLinearLayout, false)
            basicCardBinding.text = "Menu Item $menuItemNum"
            basicCardBinding.card.setOnClickListener {
                val info = """
                    Menu Item $menuItemNum
                    To scroll down, place two fingers on the screen at the same time, then swipe upwards with both of them.
                    To scroll up, swipe with two fingers in the opposite direction.
                    To finish, find the Continue button at the bottom of this vertical menu, then double tap it to continue.
                """.trimIndent()
                this.ttsEngine.speak(info)
            }
            binding.cardLinearLayout.addView(basicCardBinding.card)
        }
        val primaryButtonBinding: WidePillButtonBinding = DataBindingUtil.inflate(layoutInflater, R.layout.wide_pill_button, binding.cardLinearLayout, false)
        primaryButtonBinding.text = "Continue"
        primaryButtonBinding.button.setOnClickListener {
            // TODO continue to next fragment
        }
        binding.cardLinearLayout.addView(primaryButtonBinding.button)
    }

    /**
     * Speaks an intro for the fragment.
     * @author Andre Pham
     */
    private fun speakIntro() {
        val intro = """
            Welcome.
            In this module, you'll learn how to scroll through vertical and horizontal menus.
            To start, you'll try scrolling through a vertical menu with ${this.menuSize} items.
            To scroll down, place two fingers on the screen at the same time, then swipe upwards with both of them.
            To scroll up, swipe with two fingers in the opposite direction.
            To finish, find the Continue button at the bottom of this vertical menu, then double tap it to continue.
            You may now start.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

}