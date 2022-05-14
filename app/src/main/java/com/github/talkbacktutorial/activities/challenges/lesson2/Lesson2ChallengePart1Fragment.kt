package com.github.talkbacktutorial.activities.challenges.lesson2

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
import com.github.talkbacktutorial.activities.modules.scroll.ScrollActivity
import com.github.talkbacktutorial.activities.modules.scroll.ScrollPart1Fragment
import com.github.talkbacktutorial.activities.modules.scroll.ScrollPart2Fragment
import com.github.talkbacktutorial.databinding.BasicCardBinding
import com.github.talkbacktutorial.databinding.FragmentLesson2ChallengePart1Binding
import com.github.talkbacktutorial.databinding.FragmentScrollingModulePart1Binding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding

class Lesson2ChallengePart1Fragment : Fragment() {

    private lateinit var binding: FragmentLesson2ChallengePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson2_challenge_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as ScrollActivity))
        // Setup here
    }

    // Methods go here

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}