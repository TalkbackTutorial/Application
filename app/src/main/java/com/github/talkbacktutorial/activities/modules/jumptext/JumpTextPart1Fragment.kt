package com.github.talkbacktutorial.activities.modules.jumptext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentJumpTextPart1Binding

/**
 * Requests the user to switch to a reading mode
 *
 * @author Joel Yang
 * @see JumpTextActivity
 */
class JumpTextPart1Fragment : Fragment() {
    private lateinit var binding: FragmentJumpTextPart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    companion object {
        @JvmStatic
        fun newInstance() = JumpTextPart1Fragment()
    }

    // callback run after this fragment is created for AdjustReadingControlsActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*
            Here, we inflate our layout file (basically, turning the XML into a UI) through
            DataBindingUtil, which will provide our layout binding during the inflate process.
         */
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_jump_text_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as JumpTextActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                binding.continueCard.visibility = View.VISIBLE
            }
        this.setupContinueCard()
        this.speakIntro()
    }

    /**
     * Speaks an intro for the fragment.
     * @author Joel Yang
     */
    private fun speakIntro() {
        val intro = getString(R.string.jump_text_request_paragraph_mode).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * A card which starts a new fragment when interacted by user
     * @author Joel Yang
     */
    private fun setupContinueCard() {
        // The card starts off invisible
        binding.continueCard.visibility = View.GONE
        // The button transitions to the next fragment when clicked
        binding.continueCard.setOnClickListener {
            parentFragmentManager.commit {
                replace(this@JumpTextPart1Fragment.id, JumpTextPart2Fragment.newInstance())
//                addToBackStack("JumpReadingControlsPart2Fragment")
            }
        }
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}
