package com.github.talkbacktutorial.activities.modules.voicerecorderapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentVoiceRecorderModulePlayRecordingBinding

class VoiceRecorderPlayRecordingFragment : Fragment() {
    private lateinit var binding: FragmentVoiceRecorderModulePlayRecordingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_voice_recorder_module_play_recording,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // activate button
        binding.voiceRecorderOpenButton.setOnClickListener {
            val intent = Intent()

            // target the main activity of the forked voice recorder app
            intent.component = ComponentName("com.simplemobiletools.voicerecorder.tbtutorialfork.debug", "com.simplemobiletools.voicerecorder.activities.MainActivity")

            startActivity(intent)
        }
    }
}