package com.github.talkbacktutorial.activities.lesson0

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentLesson0Part1Binding

class Lesson0Part1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = Lesson0Part1Fragment()
    }

    private lateinit var binding: FragmentLesson0Part1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson0_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as Lesson0Activity))
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
                replace(this@Lesson0Part1Fragment.id, Lesson0Part2Fragment.newInstance())
                addToBackStack("lesson0part2")
            }
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Andre Pham
     */
    private fun speakIntro() {
        val intro = """
            Welcome. 
            In your first lesson, you'll learn to move forwards and backwards between menu items, as well as interact with them.
            Double tap to continue.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
