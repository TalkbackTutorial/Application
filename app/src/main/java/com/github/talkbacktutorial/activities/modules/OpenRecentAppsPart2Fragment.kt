package com.github.talkbacktutorial.activities.modules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.lesson0.Lesson0Activity
import com.github.talkbacktutorial.activities.lesson0.Lesson0Part2Fragment
import com.github.talkbacktutorial.databinding.FragmentOpenRecentAppsPart1Binding

class OpenRecentAppsPart2Fragment : Fragment() {

    private lateinit var binding: FragmentOpenRecentAppsPart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_open_recent_apps_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as OpenRecentAppsActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                binding.continueButton.button.visibility = View.VISIBLE
            }
        this.setupContinueButton()
        //this.speakIntro()
    }

    private fun setupContinueButton() {
        // The button starts off invisible
        binding.continueButton.button.visibility = View.GONE
        // The button transitions to the next fragment when clicked
        binding.continueButton.button.setOnClickListener {
            parentFragmentManager.commit {
                replace(this@OpenRecentAppsPart2Fragment.id, Lesson0Part2Fragment.newInstance())
                addToBackStack("lesson0part2")
            }
        }
    }
}