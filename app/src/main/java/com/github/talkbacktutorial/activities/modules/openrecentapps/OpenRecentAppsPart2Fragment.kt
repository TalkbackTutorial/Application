package com.github.talkbacktutorial.activities.modules.openrecentapps

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.HandlerCompat.postDelayed
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentOpenRecentAppsPart2Binding
import java.util.*
import kotlin.concurrent.schedule

class OpenRecentAppsPart2Fragment : Fragment() {
    private lateinit var binding: FragmentOpenRecentAppsPart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_open_recent_apps_part2,
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
            // If the count is greater than 1, the app must have lost focus and re-gained focus
            // Count changed to one here as changing fragments does not count as a focus change
            if (count > 1) {
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
            Try to open the recent apps menu by tapping the button in the bottom right corner
            of your phone. You should feel a vibration once you have tapped the button. Once again,
            open the recent apps menu and then return to the tutorial.
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
        // Delay to prevent bug where the ttsEngine is repeated.
        Timer().schedule(500) {
            ttsEngine.speak(
                "You have completed the open recent apps module. " +
                        "Sending you to the lesson screen.",
                override = true
            )
        }

    }
}
