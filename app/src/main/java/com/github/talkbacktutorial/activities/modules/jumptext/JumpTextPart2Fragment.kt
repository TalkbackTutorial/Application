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

class JumpTextPart2Fragment : Fragment() {

    private lateinit var binding: FragmentJumpTextPart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    companion object {
        @JvmStatic
        fun newInstance() = JumpTextPart2Fragment()
    }

    // callback run after this fragment is created for AdjustReadingControlsActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_jump_text_part2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as JumpTextActivity))
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
        val intro = getString(R.string.jump_text_paragraphs_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Joel Yang
     */
    private fun onClickFinishLesson() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            activity?.onBackPressed()
        }
        this.ttsEngine.speak(getString(R.string.jump_text_paragraphs_conclusion), override = true)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
