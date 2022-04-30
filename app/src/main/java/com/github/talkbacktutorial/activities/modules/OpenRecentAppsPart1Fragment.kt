package com.github.talkbacktutorial.activities.modules

import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.TextToSpeechEngine

class OpenRecentAppsPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OpenRecentAppsPart1Fragment()
    }

    private lateinit var binding: Fragment
    private lateinit var ttsEngine: TextToSpeechEngine
}