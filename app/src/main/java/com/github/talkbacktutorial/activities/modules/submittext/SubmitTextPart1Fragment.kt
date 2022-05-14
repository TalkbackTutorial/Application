package com.github.talkbacktutorial.activities.modules.submittext

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentSubmitTextPart1Binding

class SubmitTextPart1Fragment : Fragment() {

    private lateinit var binding: FragmentSubmitTextPart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private lateinit var brailleBoolButtons : Array<Boolean>
    private lateinit var brailleButtons : Array<Button>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_submit_text_part1,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as SubmitTextActivity))
        this.speakIntro()
        ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            this.setupBrailleButtons()
        }

    }

    private fun setupBrailleButtons() {
        // initialise braille button array
        this.brailleButtons = Array(7) { binding.button1 }

        brailleButtons[1] = binding.button1
        brailleButtons[2] = binding.button2
        brailleButtons[3] = binding.button3
        brailleButtons[4] = binding.button4
        brailleButtons[5] = binding.button5
        brailleButtons[6] = binding.button6

        // initialise braille boolean array
        this.brailleBoolButtons = Array(7) { false }

        // when button is clicked, switch boolean
        for (i in 1..6) {
            brailleButtons[i].setOnClickListener {
                brailleBoolButtons[i] = !brailleBoolButtons[i]
                ttsEngine.speak(i.toString())
                for (i in 0..6)
                {
                    println(i.toString() + " : " + brailleBoolButtons[i])
                }
            }
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jai Clapp
     */
    private fun speakIntro() {
        val intro = "In this module, you will learn how to submit text using the braille keyboard." +
                "To begin, type the letter A in braille.".trimIndent()
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
            val intent = Intent((activity as SubmitTextActivity), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        this.ttsEngine.speak(
            "Done",
            override = true
        )
    }
}
