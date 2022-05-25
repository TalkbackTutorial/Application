package com.github.talkbacktutorial.activities.modules.dnlbraillekeyboard

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.*
import java.util.*
import kotlin.concurrent.schedule

class DNLBrailleKeyboardPart2Fragment : Fragment(){

    private lateinit var binding: FragmentDNlBrailleKeyboardPart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dnl_braille_keyboard_part2,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as DNLBrailleKeyboardActivity))
        binding.editText.visibility = View.INVISIBLE
        this.speakIntro()
        this.setupText()
        ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            binding.editText.visibility = View.VISIBLE

        }
    }

    /**
     * Setups newe line listener.
     * @author Mohak Malhotra
     */
    private fun setupText() {
        // Adding EditorActionListener.
        binding.editText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            // When an editor action has been completed do something.
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = binding.editText.text.toString()
                val nl = getString(R.string.nl)
                // Checking for correct user input.
                if (text.lowercase() == nl) {
                    this.finishLesson()
                }
                else {
                    val error = getString(R.string.dnl_braille_part2_error).trimIndent()
                    ttsEngine.speak(error)
                }
                return@OnEditorActionListener true
            }
            false
        })
    }

    /**
     * Speaks an intro for the fragment.
     * @author Mohak Malhotra
     */
    private fun speakIntro() {
        val intro = getString(R.string.dnl_braille_part2_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * Completes the module
     * @author Mohak Malhotra
     */
    private fun finishLesson() {
        Timer().schedule(6000) {
            val outro = getString(R.string.dnl_braille_part2_outro).trimIndent()
            ttsEngine.speak(outro)
            ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                val intent = Intent((activity as DNLBrailleKeyboardActivity), MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }

    }
}
