package com.github.talkbacktutorial.activities.modules

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.modules.openrecentapps.OpenRecentAppsActivity
import com.github.talkbacktutorial.databinding.FragmentOpenRecentAppsPart1Binding

class OpenRecentAppsPart1Fragment : Fragment() {

    private lateinit var binding: FragmentOpenRecentAppsPart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_open_recent_apps_part1,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as OpenRecentAppsActivity))
            .onFinishedSpeaking(triggerOnce = true) {
            }

        this.speakIntro()
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jai Clapp
     */
    private fun speakIntro() {
        val intro = """
            Welcome.
            In this module, you'll learn how to open recent apps. This action requires a swipe left
            and then, a swipe up gesture. Please try to perform these gestures one after another.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

}