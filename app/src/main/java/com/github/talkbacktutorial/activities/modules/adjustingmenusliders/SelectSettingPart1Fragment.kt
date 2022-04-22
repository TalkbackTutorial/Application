package com.github.talkbacktutorial.activities.modules.adjustingmenusliders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.FragmentLesson2SelectsettingP1FragmentBinding

class SelectSettingPart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = SelectSettingPart1Fragment()
    }

    private lateinit var binding: FragmentLesson2SelectsettingP1FragmentBinding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson2_selectsetting_p1_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as SelectSettingModuleActivity))
            .onFinishedSpeaking(triggerOnce = true) {

            }
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

}