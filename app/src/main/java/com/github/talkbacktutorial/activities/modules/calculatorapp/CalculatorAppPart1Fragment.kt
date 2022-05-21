package com.github.talkbacktutorial.activities.modules.calculatorapp

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
//                binding.continueCard.visibility = View.VISIBLE
            }
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
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "com.simplemobiletools.calculator")

            }
        }
    }
}