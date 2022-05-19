package com.github.talkbacktutorial.activities.modules.openrecentapps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentOpenRecentAppsPart1Binding

class OpenRecentAppsPart1Fragment : Fragment() {

    private lateinit var binding: FragmentOpenRecentAppsPart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        this.speakIntro()
        // Simple listener detecting a change in window focus
        view.viewTreeObserver?.addOnWindowFocusChangeListener { _ ->
            count++
            // If the count is greater than 2, the app must have lost focus and re-gained focus
            if (count > 2) {
                finishLesson()
            }
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
            Please try to perform these gestures one after another, and enter the recent apps menu. 
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
        System.out.println("\n\nTEST\n\n")
        this.ttsEngine.speakOnInitialisation(
            "Great job. You have correctly opened the recent app menu and returned to the tutorial. " +
                    "Now to try with a different method."
        )
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            parentFragmentManager.commit {
                replace(this@OpenRecentAppsPart1Fragment.id, OpenRecentAppsPart2Fragment())
                addToBackStack("openrecentappspart1")
            }
        }
    }
}
