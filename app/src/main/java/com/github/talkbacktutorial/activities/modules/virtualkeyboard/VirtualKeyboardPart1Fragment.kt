package com.github.talkbacktutorial.activities.modules.virtualkeyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentVirtualKeyboardModulePart1Binding

class VirtualKeyboardPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = VirtualKeyboardPart1Fragment()
    }

    private lateinit var binding: FragmentVirtualKeyboardModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_virtual_keyboard_module_part1, container, false)
        this.binding.virtualKeyboardConstraintLayout.visibility = View.INVISIBLE
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as VirtualKeyboardActivity)).onFinishedSpeaking(triggerOnce = true) {
            this.binding.virtualKeyboardConstraintLayout.visibility = View.VISIBLE
        }
        this.speakIntro()
    }

    private fun speakIntro() {
        val intro = """
            In this tutorial, you will be learning how to open the on-screen virtual keyboard and 
            type using it. To start, explore by touch to find the text box then double tap to open
            the virtual keyboard. 
        """.trimIndent()

        this.ttsEngine.speakOnInitialisation(intro)
    }
}