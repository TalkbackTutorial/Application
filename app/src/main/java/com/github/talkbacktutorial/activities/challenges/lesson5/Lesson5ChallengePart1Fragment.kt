package com.github.talkbacktutorial.activities.challenges.lesson5

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
import com.github.talkbacktutorial.databinding.FragmentLesson5ChallengePart1Binding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Lesson5ChallengePart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = Lesson5ChallengePart1Fragment()
    }
    private lateinit var binding: FragmentLesson5ChallengePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private var count: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson5_challenge_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as Lesson5ChallengeActivity))
            .onFinishedSpeaking(triggerOnce = true) {
            }
        this.speakIntro()
    }

    override fun onResume() {
        // Show menu items after user back to app
        if (count == 1){
            showMenuItems(6)
        }
        super.onResume()
    }

    override fun onPause() {
        count++
        super.onPause()
    }

    /**
     * Generate two column items with time
     * @author Jason Wu
     */
    private fun showMenuItems(amount: Int) {
        val current = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        for (menuItemNum in 1..amount) {
            val basicCardBinding: BasicCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.basic_card, binding.layout1, false
            )
            basicCardBinding.text = current.plusHours(menuItemNum.toLong()).format(formatter).toString()
            basicCardBinding.card.setOnClickListener {
                val info = getString(R.string.lesson5_challenge_wrong_selected_time)
                this.ttsEngine.speak(info)
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
            if (menuItemNum == 3) {
                val currentTime = current.format(formatter)

                basicCardBinding2.text = currentTime.toString()
                basicCardBinding2.card.setOnClickListener {
                    parentFragmentManager.commit {
                        replace(this@Lesson5ChallengePart1Fragment.id, Lesson5ChallengePart2Fragment.newInstance())
                        addToBackStack("lesson5ChallengePart2")
                    }
                }
            } else {
                basicCardBinding2.text = current.minusHours(menuItemNum.toLong()).format(formatter).toString()
                basicCardBinding2.card.setOnClickListener {
                    val info = getString(R.string.lesson5_challenge_wrong_selected_time)
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
        val intro = getString(R.string.lesson5_challenge_fragment1_intro)
        this.ttsEngine.speakOnInitialisation(intro)
    }
}