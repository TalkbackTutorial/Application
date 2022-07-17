package com.github.talkbacktutorial.activities.modules.calculatorapp

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
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
        this.setupInstallPrompt()
        this.speakIntro()
    }

    override fun onResume() {
        super.onResume()
        // check on return if app installed
        this.setupInstallPrompt()
    }

    /**
     * Speaks an intro for the fragment.
     * @author Joel Yang
     */
    private fun speakIntro() {
        val intro = getString(R.string.calculator_app_part1_intro).trimIndent()
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

    /**
     * Sets up a message telling the user to install the app. It only appears if the calculator app
     * wasn't found on the device.
     *
     * @author Matthew Crossman
     */
    private fun setupInstallPrompt() {
        if (!checkInstalled())
            binding.installPrompt.visibility = View.VISIBLE
        else
            binding.installPrompt.visibility = View.GONE
    }

    /**
     * Checks if calculator app is installed
     *
     * @author Matthew Crossman
     *
     * @return true if calculator was found
     */
    private fun checkInstalled(): Boolean {
        val pm = context?.packageManager

        pm?.let {
            val appAvailable: Boolean = try {
                pm.getPackageInfo("com.simplemobiletools.calculator.debug", 0)
                true
            } catch (_: PackageManager.NameNotFoundException) {
                false
            }
            return appAvailable;
        }
        return false;
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }
}