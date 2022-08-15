package com.github.talkbacktutorial.activities.challenges.lesson5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentLesson5ChallengePart1Binding

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
        this.speakIntro()
    }

    override fun onResume() {
        // Jump to second task after user return to the app
        if (count == 1){
            parentFragmentManager.commit {
                replace(this@Lesson5ChallengePart1Fragment.id, Lesson5ChallengePart2Fragment.newInstance())
                addToBackStack(getString(R.string.lesson5_challenge_part2_backstack))
            }
        }
        super.onResume()
    }

    override fun onPause() {
        count++
        super.onPause()
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jason Wu
     */
    private fun speakIntro() {
        val intro = getString(R.string.lesson5_challenge_intro) + getString(R.string.lesson5_challenge_fragment1_intro)
        this.ttsEngine.speakOnInitialisation(intro)
    }
}