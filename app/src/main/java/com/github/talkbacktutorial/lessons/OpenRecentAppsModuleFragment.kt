package com.github.talkbacktutorial.lessons

import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.lesson0.Lesson0Part1Fragment
import com.github.talkbacktutorial.databinding.FragmentLesson0Part1Binding

class OpenRecentAppsModuleFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OpenRecentAppsModuleFragment()
    }

    private lateinit var binding: FragmentL
    private lateinit var ttsEngine: TextToSpeechEngine
}