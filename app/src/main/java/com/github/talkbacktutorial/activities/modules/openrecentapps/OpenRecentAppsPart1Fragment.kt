package com.github.talkbacktutorial.activities.modules.openrecentapps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
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
            In this module, you'll learn how to open recent apps. Recent apps are a handy way to
            quickly switch between frequently used apps. This can be done in two different ways. 
            Firstly, perform a swipe left and then, a swipe up gesture. 
            Please try to perform these gestures one after another and enter a different app. 
            Once completed, return to the tutorial.
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
            parentFragmentManager.commit {
                replace(this@OpenRecentAppsPart1Fragment.id, OpenRecentAppsPart2Fragment())
                addToBackStack("openrecentappspart1")
            }
        }
        this.ttsEngine.speak("Great job. You have correctly opened the recent app menu and" +
                "returned to the tutorial." + "Now to try with a different method.", override = true)
    }

}

