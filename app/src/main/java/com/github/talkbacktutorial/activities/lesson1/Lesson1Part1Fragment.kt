package com.github.talkbacktutorial.activities.lesson1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentLesson1Part1Binding

class Lesson1Part1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = Lesson1Part1Fragment()
    }

    private lateinit var binding: FragmentLesson1Part1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson1_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as Lesson1Activity))
            .onFinishedSpeaking(triggerOnce = true) {
                binding.continueButton.button.visibility = View.VISIBLE
            }
        this.setupContinueButton()
        this.speakIntro()
    }

    /**
     * Sets up the continue button's visibility and listener.
     * @author Andre Pham
     */
    private fun setupContinueButton() {
        // The button starts off invisible
        binding.continueButton.button.visibility = View.GONE
        // The button transitions to the next fragment when clicked
        binding.continueButton.button.setOnClickListener {
            parentFragmentManager.commit {
                replace(this@Lesson1Part1Fragment.id, Lesson1Part2Fragment.newInstance())
                addToBackStack(getString(R.string.lesson1_part2_backstack))
            }
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Andre Pham
     */
    private fun speakIntro() {
        val intro = getString(R.string.lesson1_part1_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
