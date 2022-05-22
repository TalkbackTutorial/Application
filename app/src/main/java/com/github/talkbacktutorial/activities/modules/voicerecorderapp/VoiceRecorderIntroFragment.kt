package com.github.talkbacktutorial.activities.modules.voicerecorderapp

import android.os.Bundle
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

        binding.voiceRecorderIntroContinue.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.frame, VoiceRecorderMakeRecordingFragment())
            }
        }
    }

}