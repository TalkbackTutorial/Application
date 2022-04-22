package com.github.talkbacktutorial.activities.modules.mediavolumecontrol

import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentMediaVolumeControlModulePart1Binding

class MediaVolumeControlPart1Fragment: Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MediaVolumeControlPart1Fragment()
    }

    private lateinit var binding: FragmentMediaVolumeControlModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine
}