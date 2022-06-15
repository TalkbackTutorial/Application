package com.github.talkbacktutorial.activities.modules.openingvoicecommand

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentOpenVoiceCommandModulePart1Binding

class OpenVoiceCommandPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OpenVoiceCommandPart1Fragment()
    }

    private lateinit var binding: FragmentOpenVoiceCommandModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

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
            .onFinishedSpeaking(triggerOnce = true) {
                this.speakOutro()
            }
        this.speakIntro()
    }

    /**
     * Speaks an intro for the fragment.
     * @author Mohak Malhotra
     */
    private fun speakIntro() {
        val intro = getString(R.string.open_voice_commands_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /** Speaks an outro for the fragment
     * @author Mohak Malhotra
     *  */
    private fun speakOutro() {
        val outro = getString(R.string.open_voice_commands_outro).trimIndent()
        this.ttsEngine.speak(outro)
    }

    private fun Fragment.removeOnWindowFocusChangeListener(callback: (hasFocus: Boolean) -> Unit) =
        view?.viewTreeObserver?.removeOnWindowFocusChangeListener(callback)

    /**
     * To do when finish the lesson
     * @author Mohak Malhotra
     */
    private fun finishLesson() {
        removeOnWindowFocusChangeListener {}
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent =
                Intent((activity as OpenVoiceCommandActivity), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        this.speakOutro()
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
