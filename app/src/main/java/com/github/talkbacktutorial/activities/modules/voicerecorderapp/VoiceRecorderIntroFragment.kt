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
import com.github.talkbacktutorial.databinding.FragmentVoiceRecorderModuleIntroBinding

/**
 * Introductory fragment asking user to install the forked app.
 *
 * @author Matthew Crossman
 */
class VoiceRecorderIntroFragment : Fragment() {

    private lateinit var binding: FragmentVoiceRecorderModuleIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_voice_recorder_module_intro,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.voiceRecorderIntroContinue.isEnabled = checkInstalled()

        binding.voiceRecorderAppLink.movementMethod = LinkMovementMethod.getInstance()

        binding.voiceRecorderIntroContinue.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.frame, VoiceRecorderMakeRecordingFragment())
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // check installation status on return
        binding.voiceRecorderIntroContinue.isEnabled = checkInstalled()
    }

    private fun checkInstalled(): Boolean {
        val pm = context?.packageManager

        pm?.let {
            val appAvailable: Boolean = try {
                pm.getPackageInfo(VoiceRecorderAppActivity.packageName, 0)
                true
            } catch (_: PackageManager.NameNotFoundException) {
                false
            }

            return appAvailable;
        }

        return false;
    }

}