package com.github.talkbacktutorial.activities.modules.submittext

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.*
import java.util.*
import kotlin.concurrent.schedule


class SubmitTextPart1Fragment : Fragment(){

    private lateinit var binding: FragmentSubmitTextPart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

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
        ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            binding.editText.visibility = View.VISIBLE

        }
        this.speakIntro()
        this.setupText()
    }

    /**
     * Setups text submit listener.
     * @author Jai Clapp
     */
    private fun setupText() {
        // Adding EditorActionListener.
        binding.editText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            // When an editor action has been completed (submit), do something.
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = binding.editText.text.toString()
                // Checking for correct user input.
                if (text.lowercase() == "a") {
                    this.finishLesson()
                }
                else {
                    ttsEngine.speak("Incorrect letter submitted. Try again.")
                }
                return@OnEditorActionListener true
            }
            false
        })
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jai Clapp
     */
    private fun speakIntro() {
        val intro = "In this module, you will learn how to submit text using the braille keyboard." +
                "Ensure the braille keyboard is active and set to default. If you are unsure how to" +
                "do this, refer to a previous module. To begin, type the letter a in braille and " +
                "then submit the text. To submit, use a two finger swipe up gesture. Note, the braille" +
                "keyboard must be in the correct orientation for this gesture to work correctly".trimIndent()
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
        Timer().schedule(5000) {
            ttsEngine.speak(
                "Great job. You correctly typed in the letter A.".trimIndent()
            )
            ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                parentFragmentManager.commit {
                    replace(this@SubmitTextPart1Fragment.id, SubmitTextPart2Fragment())
                    addToBackStack("submittextpart1")
                }
            }
        }

    }
}
