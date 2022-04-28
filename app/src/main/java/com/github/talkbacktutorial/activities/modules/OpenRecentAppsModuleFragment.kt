package com.github.talkbacktutorial.activities.modules

import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.TextToSpeechEngine

class OpenRecentAppsModuleFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OpenRecentAppsModuleFragment()
    }

    private lateinit var binding: Fragment
    private lateinit var ttsEngine: TextToSpeechEngine
}