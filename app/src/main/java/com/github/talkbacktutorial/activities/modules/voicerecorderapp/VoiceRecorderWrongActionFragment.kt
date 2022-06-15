package com.github.talkbacktutorial.activities.modules.voicerecorderapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.voicerecorderapp.VoiceRecorderAppActivity.VoiceRecorderStage
import com.github.talkbacktutorial.databinding.FragmentVoiceRecorderModuleWrongBinding

class VoiceRecorderWrongActionFragment(private val stage: VoiceRecorderStage) : Fragment() {

    lateinit var binding: FragmentVoiceRecorderModuleWrongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_voice_recorder_module_wrong,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set textview
        when (stage) {
            VoiceRecorderStage.MAKE_RECORDING -> binding.voiceRecorderRepeatPrompt.setText(R.string.voice_recorder_make_recording_prompt)
            VoiceRecorderStage.PLAY_RECORDING -> binding.voiceRecorderRepeatPrompt.setText(R.string.voice_recorder_play_recording_prompt)
            else -> binding.voiceRecorderRepeatPrompt.setText(R.string.voice_recorder_wrong_bad_stage)
        }

        // button setup
        binding.voiceRecorderWrongOpenApp.setOnClickListener {
            startActivity(VoiceRecorderAppActivity.getAppIntent())
        }
    }
}