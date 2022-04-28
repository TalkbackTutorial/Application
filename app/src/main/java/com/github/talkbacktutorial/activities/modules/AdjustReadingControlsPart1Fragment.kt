package com.github.talkbacktutorial.activities.modules

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentAdjustReadingControlsPart1Binding

/**
 * The first and currently only module to learn how to adjust reading controls.
 *
 * @author Matthew Crossman
 * @see AdjustReadingControlsActivity
 */
class AdjustReadingControlsPart1Fragment : Fragment() {
    private lateinit var binding: FragmentAdjustReadingControlsPart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    // callback run after this fragment is created for AdjustReadingControlsActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*
            Here, we inflate our layout file (basically, turning the XML into a UI) through
            DataBindingUtil, which will provide our layout binding during the inflate process.
         */
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adjust_reading_controls_part1, container, false)
        return binding.root
    }

    // callback run after this fragment has replaced AdjustReadingControlsActivity's R.id.frame
    // it now exists as a part of the UI, so we can now start working with its layout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // show the multi-finger info if on a version of android that supports them
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P)
            binding.multiFingerPrompt.visibility = View.GONE

        // makes button leave the fragment
        binding.finishModule.setOnClickListener {
            activity?.onBackPressed()
        }

        this.speakIntro()
    }

    private fun speakIntro() {
        val intro = """
            We'll be learning how to adjust Talkback's reading controls, which lets us make TalkBack read and navigate
            in different ways. Talkback has reading modes, such as word-by-word, and navigation modes, such as jumping 
            from header to header. When you change modes, Talkback will speak the mode you switched to.
            
            To change your mode, swipe up and down in one motion. Take your time to cycle through all the choices.
            
            You can go  backwards through the list. Swipe down then up in one motion to go through the list in opposite direction.
            
            Modes can also bee switched by swiping with three fingers horizontally to the left or to the right.
            
            That's all there is to changing modes. The rest of this lesson covers how to use these modes. Activate the button below to finish this module.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}