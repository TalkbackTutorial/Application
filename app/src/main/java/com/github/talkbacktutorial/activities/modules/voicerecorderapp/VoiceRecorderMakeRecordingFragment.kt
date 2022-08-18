package com.github.talkbacktutorial.activities.modules.voicerecorderapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentVoiceRecorderModuleMakeRecordingBinding

/**
 * Fragment where user tries to make a voice recording.
 *
 * @author Matthew Crossman
 */
class VoiceRecorderMakeRecordingFragment : Fragment() {

    private lateinit var binding: FragmentVoiceRecorderModuleMakeRecordingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_voice_recorder_module_make_recording,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.prompt.text = getString(R.string.voice_recorder_make_recording_intro) + "\n\n" + getString(R.string.voice_recorder_make_recording_prompt)

        binding.voiceRecorderOpenButton.setOnClickListener {
            startActivity(VoiceRecorderAppActivity.getAppIntent())
        }
    }

}