package com.github.talkbacktutorial.activities.modules.startstopmedia

import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentStartStopMediaModulePart1Binding

class StartStopMediaPart1Fragment: Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = StartStopMediaPart1Fragment()
    }

    private lateinit var binding: FragmentStartStopMediaModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
}