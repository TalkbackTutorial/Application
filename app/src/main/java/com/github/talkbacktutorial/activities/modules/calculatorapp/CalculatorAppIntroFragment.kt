package com.github.talkbacktutorial.activities.modules.voicerecorderapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.calculatorapp.CalculatorAppPart1Fragment
import com.github.talkbacktutorial.databinding.FragmentCalculatorAppModuleIntroBinding
import com.github.talkbacktutorial.databinding.FragmentVoiceRecorderModuleIntroBinding

/**
 * Introductory fragment asking user to install the forked app.
 *
 * @author Joel Yang
 * @see
 */
class CalculatorAppIntroFragment : Fragment() {

    private lateinit var binding: FragmentCalculatorAppModuleIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_calculator_app_module_intro,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calculatorIntroContinue.isEnabled = checkInstalled()
        binding.calculatorLink.movementMethod = LinkMovementMethod.getInstance()
        binding.calculatorIntroContinue.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.frame, CalculatorAppPart1Fragment())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // check installation status on return
        binding.calculatorIntroContinue.isEnabled = checkInstalled()
    }

    /**
     * Checks whether the relevant calculator app is currently installed.
     *
     * @return true if the desired package (Calc-fork) is installed, false otherwise
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
            return appAvailable
        }
        return false
    }

}