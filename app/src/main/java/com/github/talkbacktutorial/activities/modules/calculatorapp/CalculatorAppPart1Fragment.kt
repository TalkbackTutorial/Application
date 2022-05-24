package com.github.talkbacktutorial.activities.modules.calculatorapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.modules.jumptext.JumpTextActivity
import com.github.talkbacktutorial.databinding.FragmentCalculatorAppModulePart1Binding

class CalculatorAppPart1Fragment : Fragment() {
    private lateinit var binding: FragmentCalculatorAppModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    companion object {
        @JvmStatic
        fun newInstance() = CalculatorAppPart1Fragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_calculator_app_module_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as CalculatorAppActivity))
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
        val intro = "In this module, you will apply what you have learnt from the previous lessons to use the simple calculator app. You will be given a simple calculation task to work on. Unless you complete this task successfully, we will let you retry as many times".trimIndent()
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
            val intent = Intent()

            // target the main activity of the forked calculator recorder app
            intent.component = ComponentName("com.simplemobiletools.calculator.debug", "com.simplemobiletools.calculator.activities.SplashActivity.Orange")

            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}