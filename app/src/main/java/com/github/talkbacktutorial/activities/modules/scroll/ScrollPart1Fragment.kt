package com.github.talkbacktutorial.activities.modules.scroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.core.view.allViews
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.BasicCardBinding
import com.github.talkbacktutorial.databinding.FragmentScrollingModulePart1Binding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding

class ScrollPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = ScrollPart1Fragment()
    }

    private lateinit var binding: FragmentScrollingModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private val menuSize = 50

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scrolling_module_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as ScrollActivity))
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
            val basicCardBinding: BasicCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.basic_card, binding.cardLinearLayout, false
            )
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
            parentFragmentManager.commit {
                replace(
                    this@ScrollPart1Fragment.id,
                    ScrollPart2Fragment.newInstance()
                )
                addToBackStack("scrollingModulePart2")
            }
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
            Screens often contain very long lists that contain more items than the screen can show at once.
            Scrolling is used to go through these very long lists of items very quickly.
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
