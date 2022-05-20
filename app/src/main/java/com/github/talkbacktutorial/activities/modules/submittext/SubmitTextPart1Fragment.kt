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
                    val error = getString(R.string.submit_text_part1_error).trimIndent()
                    ttsEngine.speak(error)
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
        val intro = getString(R.string.submit_text_part1_intro).trimIndent()
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
            val outro = getString(R.string.submit_text_part1_outro).trimIndent()
            ttsEngine.speak(outro)
            ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                parentFragmentManager.commit {
                    replace(this@SubmitTextPart1Fragment.id, SubmitTextPart2Fragment())
                    addToBackStack("submittextpart1")
                }
            }
        }

    }
}
