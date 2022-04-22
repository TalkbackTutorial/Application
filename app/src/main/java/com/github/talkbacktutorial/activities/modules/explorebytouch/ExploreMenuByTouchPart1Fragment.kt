package com.github.talkbacktutorial.activities.modules.explorebytouch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.BasicCardBinding
import com.github.talkbacktutorial.databinding.FragmentExploreMenuByTouchModulePart1Binding


/**
 * Part1 Fragment of explore menu by touch module
 * @author Jason Wu
 */
class ExploreMenuByTouchPart1Fragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = ExploreMenuByTouchPart1Fragment()
    }
    private lateinit var binding: FragmentExploreMenuByTouchModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore_menu_by_touch_module_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as ExploreMenuByTouchActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.showMenuItems(8)
            }
        this.speakIntro()
    }

    /**
     * Generate two column menu - allow explore in both vertical and horizontal
     * @author Jason Wu
     */
    private fun showMenuItems(amount: Int) {
        for (menuItemNum in 1..amount) {
            val basicCardBinding: BasicCardBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.basic_card, binding.layout1, false)
            basicCardBinding.text = "Row $menuItemNum Column 1"
            basicCardBinding.card.setOnClickListener {
                val info = """
                    Row $menuItemNum Column 1.
                    Touch the screen with your finger, then drag it slowly around the screen.
                    You can drag your finger in any direction and don't leave the screen.
                    Find the continue button and double tap it.
                """.trimIndent()
                this.ttsEngine.speak(info)
            }
            binding.layout1.addView(basicCardBinding.card)
            if (menuItemNum == 1) basicCardBinding.card.sendAccessibilityEvent(
                AccessibilityEvent.TYPE_VIEW_FOCUSED)

            // Create the second column
            val basicCardBinding2: BasicCardBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.basic_card, binding.layout2, false)
            if (menuItemNum == 5){
                basicCardBinding2.text = "Continue Button"
                basicCardBinding2.card.setOnClickListener {
                parentFragmentManager.commit {
                    replace(this@ExploreMenuByTouchPart1Fragment.id, ExploreMenuByTouchPart2Fragment.newInstance())
                    addToBackStack("exploreMenuModulePart2") }
                 }
            }
            else{
                basicCardBinding2.text = "Row $menuItemNum Column 2"
                basicCardBinding2.card.setOnClickListener {
                    val info = """
                    Row $menuItemNum Column 2.
                    Touch the screen with your finger, then drag it slowly around the screen.
                    You can drag your finger in any direction and don't leave the screen.
                    Find the continue button and double tap it.
                """.trimIndent()
                    this.ttsEngine.speak(info)
                }
            }

            binding.layout2.addView(basicCardBinding2.card)
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jason Wu
     */
    private fun speakIntro() {
        val intro = """
            Welcome! 
            We will mainly focus on interact with menu in this lesson.
            In this module, you will learn how to explore the menu.
            Usually there are many items in the menu, and you may want to browse them more efficiently.
            Touching and dragging are used to go through the menu items more quickly.
            To start, you will explore a two column menu using this method.
            Touch the screen by your finger and drag it slowly around screen.
            Make sure your finger is keep touching the screen.
            You can drag your finger to any direction, either up, down, right or left.
            Find the continue button in the menu, double tap it to continue.
        """.trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}