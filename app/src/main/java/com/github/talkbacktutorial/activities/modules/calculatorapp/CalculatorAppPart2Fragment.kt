package com.github.talkbacktutorial.activities.modules.calculatorapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentCalculatorAppModulePart2Binding

class CalculatorAppPart2Fragment : Fragment() {
    private lateinit var binding: FragmentCalculatorAppModulePart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    companion object {
        @JvmStatic
        fun newInstance() = CalculatorAppPart2Fragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_calculator_app_module_part2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as CalculatorAppActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                binding.continueCard.visibility = View.VISIBLE
            }
        this.setupFinishCard()
        this.speakIntro()
    }

    /**
     * Speaks an intro for the fragment.
     * @author Joel Yang
     */
    private fun speakIntro() {
        val intro = getString(R.string.calculator_app_part2_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * A card which sends user back to the module when interacted by user
     * @author Joel Yang
     */
    private fun setupFinishCard() {
        // The card starts off invisible
        binding.continueCard.visibility = View.GONE
        // The button transitions to the next fragment when clicked
        binding.continueCard.setOnClickListener {
            this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                activity?.onBackPressed()
            }
            this.ttsEngine.speak(getString(R.string.finish_calculator_module), override = true)
        }
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}