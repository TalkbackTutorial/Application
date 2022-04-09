package com.github.talkbacktutorial.activities.lesson0

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentLesson0Part1Binding

class Lesson0Part1Fragment : Fragment() {

    private lateinit var binding: FragmentLesson0Part1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson0_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueButton.button.visibility = View.GONE

        this.ttsEngine = TextToSpeechEngine((activity as Lesson0Activity))
            .onFinishedSpeaking(triggerOnce = true) {
                binding.continueButton.button.visibility = View.VISIBLE
            }

        binding.continueButton.button.setOnClickListener {
            parentFragmentManager.commit {
                replace(this@Lesson0Part1Fragment.id, Lesson0Part2Fragment.newInstance())
                addToBackStack("lesson0part2")
            }
        }

        val intro = """
            Welcome. 
            In your first lesson, you'll learn to move forwards and backwards between menu items, as well as interact with them.
            Double tap to continue.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    companion object {
        @JvmStatic
        fun newInstance() = Lesson0Part1Fragment()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

}