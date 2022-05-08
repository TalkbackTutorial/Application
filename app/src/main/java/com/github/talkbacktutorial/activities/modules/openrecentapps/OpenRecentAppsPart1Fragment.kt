package com.github.talkbacktutorial.activities.modules.openrecentapps

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentOpenRecentAppsPart1Binding


class OpenRecentAppsPart1Fragment : Fragment(), DefaultLifecycleObserver {

    private lateinit var binding: FragmentOpenRecentAppsPart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private var count = 0

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
        // Adding the fragment as the view lifecycle observer
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as OpenRecentAppsActivity))
            .onFinishedSpeaking(triggerOnce = true) {
            }

        this.speakIntro()
    }

    override fun onPause() {
        super<Fragment>.onPause()
        super<DefaultLifecycleObserver>.onPause(this)
        // Increment the count when the application is paused
        count++
    }

    override fun onResume() {
        super<Fragment>.onResume()
        super<DefaultLifecycleObserver>.onPause(this)
        // Very simple check if the application has been paused previously
        if (count > 0) {
            finishLesson()
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jai Clapp
     */
    private fun speakIntro() {
        val intro = """
            Welcome.
            In this module, you'll learn how to open recent apps. This action requires a swipe left
            and then, a swipe up gesture. Please try to perform these gestures one after another and
            enter a different app. Once completed, return to the tutorial.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * Completes the module
     * @author Jai Clapp
     */
    private fun finishLesson() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent = Intent((activity as OpenRecentAppsActivity), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        this.ttsEngine.speak("You have completed the open recent apps module. " +
                "Sending you to the main menu.", override = true)
    }

}

