package com.github.talkbacktutorial.activities.modules.jumpreadingcontrols

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentJumpReadingControlsPart2Binding

/**
 * Instantiates a UI for user to interact with controls mode
 *
 * @author Joel Yang
 * @see AdjustReadingControlsActivity
 */
class JumpReadingControlsPart2Fragment : Fragment() {
    private lateinit var binding: FragmentJumpReadingControlsPart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine

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
        this.speakIntro()
    }

    private fun speakIntro() {
        val intro = """
  
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}