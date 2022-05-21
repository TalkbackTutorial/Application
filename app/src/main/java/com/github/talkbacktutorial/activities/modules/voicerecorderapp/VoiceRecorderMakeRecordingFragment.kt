package com.github.talkbacktutorial.activities.modules.voicerecorderapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentVoiceRecorderModuleMakeRecordingBinding

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

        binding.voiceRecorderOpenButton.setOnClickListener {
            val intent = Intent()

            // target the main activity of the forked voice recorder app
            intent.component = ComponentName("com.simplemobiletools.voicerecorder.tbtutorialfork.debug", "com.simplemobiletools.voicerecorder.activities.MainActivity")

            startActivity(intent)
        }
    }

}