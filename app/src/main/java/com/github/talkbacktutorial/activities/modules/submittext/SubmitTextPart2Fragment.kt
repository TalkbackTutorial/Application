package com.github.talkbacktutorial.activities.modules.submittext

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.FragmentJumpNavigationModulePart1Binding
import com.github.talkbacktutorial.databinding.FragmentSubmitTextPart2Binding
import java.util.*
import kotlin.concurrent.schedule

class SubmitTextPart2Fragment : Fragment(){

    private lateinit var binding: FragmentSubmitTextPart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine
    private lateinit var brailleBoolButtons : Array<Int>
    private lateinit var brailleButtons : Array<Button>
    private lateinit var brailleDict : Map<String, String>
    private lateinit var letterContainer : Queue<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_submit_text_part2,
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
            this.setupBrailleDict()
            letterContainer = LinkedList()
        }

    }

    /**
     * Setup braille dictionary to convert digits to letters.
     * @author Jai Clapp
     */
    private fun setupBrailleDict() {
        brailleDict = mapOf("0100000" to "a", "0110000" to "b", "0100100" to "c", "0100110" to "d",
            "0100010" to "e", "0110100" to "f", "0110110" to "g", "0110010" to "h", "0010100" to "i",
            "0010110" to "j", "0101000" to "k", "0111000" to "l", "0101100" to "m", "0101110" to "n",
            "0101010" to "o", "0111100" to "p", "0111110" to "q", "0111010" to "r", "0011100" to "s",
            "0011110" to "t", "0101001" to "u", "0111001" to "v", "0010111" to "w", "0101101" to "x",
            "0101111" to "y", "0101011" to "z")
    }

    /**
     * Setup braille buttons to perform actions when clicked.
     * @author Jai Clapp
     */
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
        this.brailleBoolButtons = Array(7) { 0 }

        // when button is clicked, switch boolean
        for (i in 1..6) {
            brailleButtons[i].setOnClickListener {
                brailleBoolButtons[i] = brailleBoolButtons[i].not()
                Timer().schedule(2000) {
                    val letter = brailleDict[arrayToBraille()]
                    brailleDict[arrayToBraille()]?.let { it1 -> ttsEngine.speak(it1) }
                    letterContainer.add(letter)
                    if (letter == "a") {
                         finishLesson()
                    }
                }


                // After 5 seconds, revert changes
                Timer().schedule(1000) {
                    brailleBoolButtons[i] = brailleBoolButtons[i].not()
                }
            }
        }
    }

    /**
     * Converts an array of braille characters to string.
     * @author Jai Clapp
     */
    private fun arrayToBraille(): String {
        return brailleBoolButtons.joinToString("")
    }

    /**
     * Converts Integer 1 to 0 and vice versa.
     * @author Jai Clapp
     */
    private fun Int.not(): Int { return if (this == 1) 0 else 1 }

    /**
     * Speaks an intro for the fragment.
     * @author Jai Clapp
     */
    private fun speakIntro() {
        val intro = ("Now that you have correctly typed the letter A, try to write the word apple.")
            .trimIndent()
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
            "Great job. You correctly typed in the letter A.",
            override = true
        )
    }
}