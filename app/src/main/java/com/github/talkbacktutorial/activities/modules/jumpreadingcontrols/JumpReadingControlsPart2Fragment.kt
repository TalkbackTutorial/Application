package com.github.talkbacktutorial.activities.modules.jumpreadingcontrols

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.LessonActivity
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.activities.lesson0.Lesson0Activity
import com.github.talkbacktutorial.activities.lesson0.Lesson0Part1Fragment
import com.github.talkbacktutorial.activities.lesson0.Lesson0Part2Fragment
import com.github.talkbacktutorial.databinding.FragmentJumpReadingControlsPart1Binding
import com.github.talkbacktutorial.databinding.FragmentJumpReadingControlsPart2Binding
import com.github.talkbacktutorial.lessons.Lesson3

/**
 * Instantiates a UI for user to interact with header mode
 *
 * @author Joel Yang
 * @see AdjustReadingControlsActivity
 */
class JumpReadingControlsPart2Fragment : Fragment() {
    private lateinit var binding: FragmentJumpReadingControlsPart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    companion object {
        @JvmStatic
        fun newInstance() = JumpReadingControlsPart2Fragment()
    }

    // callback run after this fragment is created for AdjustReadingControlsActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        /*
            Here, we inflate our layout file (basically, turning the XML into a UI) through
            DataBindingUtil, which will provide our layout binding during the inflate process.
         */
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jump_reading_controls_part2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as JumpReadingControlsActivity))
            .onFinishedSpeaking(triggerOnce = true) {
            }
        this.speakIntro()
        binding.finish.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    /**
     * Speaks an intro for the fragment.
     * @author Joel Yang
     */
    private fun speakIntro() {
        val intro = """
            You will now learn to transition between paragraphs. A single swipe up or down will transition between different paragraphs that are on the screen. Please try it
            until you reach a button that says finish lesson, then double tap to finish the lesson.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Joel Yang
     */
    private fun onClickFinishLesson(){
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent = Intent((activity as JumpReadingControlsActivity), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        this.ttsEngine.speak("You have completed the lesson..." +
                "To summarize, in this lesson you have learnt to use the Paragraph reading mode to navigate through the page..." +
                "You can also use what you have learnt on other reading modes next time!" +
                "Sending you back to the Lesson modules.", override = true)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}