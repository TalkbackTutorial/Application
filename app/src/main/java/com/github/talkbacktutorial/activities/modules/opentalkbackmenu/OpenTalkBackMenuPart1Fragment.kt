package com.github.talkbacktutorial.activities.modules.opentalkbackmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentOpenTalkbackMenuModulePart1Binding

class OpenTalkBackMenuPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OpenTalkBackMenuPart1Fragment()
    }

    private lateinit var binding: FragmentOpenTalkbackMenuModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_open_notification_module_part1, container, false)
        return binding.root
    }
}