package com.github.talkbacktutorial.activities.lesson1

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
import com.github.talkbacktutorial.databinding.FragmentLesson1Part2Binding

class Lesson1Part2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = Lesson1Part2Fragment()
    }

    private lateinit var binding: FragmentLesson1Part2Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson1_part2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as Lesson1Activity))
            .onFinishedSpeaking(triggerOnce = true) {
                this.showMenuItems(8)
            }
        this.speakIntro()
    }

    /**
     * Shows a range of menu items. Interacting with any starts the next fragment.
     * @param amount The number of menu items to show
     * @author Andre Pham
     */
    private fun showMenuItems(amount: Int) {
        for (menuItemNum in 1..amount) {
            val basicCardBinding: BasicCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.basic_card, binding.cardLinearLayout, false
            )
            basicCardBinding.text = "Menu Item $menuItemNum"
            basicCardBinding.card.setOnClickListener {
                parentFragmentManager.commit {
                    replace(this@Lesson1Part2Fragment.id, Lesson1Part3Fragment.newInstance())
                    addToBackStack("lesson0part3")
                }
            }
            binding.cardLinearLayout.addView(basicCardBinding.card)
            if (menuItemNum == 1) basicCardBinding.card.sendAccessibilityEvent(
                AccessibilityEvent.TYPE_VIEW_FOCUSED
            )
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Andre Pham
     */
    private fun speakIntro() {
        val intro = """
            To start, you will learn to navigate back and forth between elements on the screen.
            To go to the next element, swipe right.
            To go to the previous element, swipe left.
            Elements will always speak a description of itself.
            When you're ready to move on, double tap.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
