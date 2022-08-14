package com.github.talkbacktutorial.activities.modules.openingvoicecommand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentOpenVoiceCommandModulePart1Binding
import java.util.*
import kotlin.concurrent.schedule

class OpenVoiceCommandPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OpenVoiceCommandPart1Fragment()
    }

    private lateinit var binding: FragmentOpenVoiceCommandModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_open_voice_command_module_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as OpenVoiceCommandActivity))
        this.speakIntro()

        view.viewTreeObserver?.addOnWindowFocusChangeListener { _ ->
            count++
            // If the count is greater than 2, the app must have lost focus and re-gained focus
            if (count == 3) {
                finishLesson()
            }
        }
    }


    /**
     * Speaks an intro for the fragment.
     * @author Mohak Malhotra & Jai Clapp
     */
    private fun speakIntro() {
        val intro = getString(R.string.open_voice_commands_part1_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * To do when finish the lesson
     * @author Mohak Malhotra & Jai Clapp
     */
    private fun finishLesson() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            parentFragmentManager.commit {
                replace(this@OpenVoiceCommandPart1Fragment.id, OpenVoiceCommandPart2Fragment.newInstance())
                addToBackStack(getString(R.string.open_recent_apps_part1_backstack))
            }
        }
        Timer().schedule(3000) {
            val outro = getString(R.string.open_voice_commands_part1_outro).trimIndent()
            ttsEngine.speak(outro)
        }
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
