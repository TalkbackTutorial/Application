package com.github.talkbacktutorial.activities.modules.jumptext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentJumpTextPart2Binding

/**
 * Instantiates a UI for user to interact with header mode
 *
 * @author Joel Yang
 * @see AdjustReadingControlsActivity
 */
class JumpTextPart2Fragment : Fragment() {
    private lateinit var binding: FragmentJumpTextPart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    companion object {
        @JvmStatic
        fun newInstance() = JumpTextPart2Fragment()
    }

    // callback run after this fragment is created for AdjustReadingControlsActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        /*
            Here, we inflate our layout file (basically, turning the XML into a UI) through
            DataBindingUtil, which will provide our layout binding during the inflate process.
         */
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jump_text_part2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as JumpTextActivity))
            .onFinishedSpeaking(triggerOnce = true) {
            }
        this.speakIntro()
        binding.finish.setOnClickListener {
            this.onClickFinishLesson()
        }

    }

    /**
     * Speaks an intro for the fragment.
     * @author Joel Yang
     */
    private fun speakIntro() {
        val intro = """
            You will now learn to transition between paragraphs. A single swipe up or down will transition between different paragraphs that are on the screen. Please try it
            until you reach a button that says finish lesson, then double tap to finish the lesson. For your information, this will be a very long practice text so it would be easier to complete it using the Paragraph reading mode.
            However, you can still try out different reading modes such as Characters, Words and lines reading modes
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Joel Yang
     */
    private fun onClickFinishLesson(){
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            activity?.onBackPressed()
        }
        this.ttsEngine.speak("You have completed the lesson..." +
                "To summarize, in this lesson you have learnt to use the Paragraph reading mode to navigate through the page..." +
                "In the future, you can utilize these different reading modes such as Headers, Controls, Links and more, to navigate through your phone." +
                "Sending you back to the Lesson modules.", override = true)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}