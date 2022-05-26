package com.github.talkbacktutorial.activities.modules.voicerecorderapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentVoiceRecorderModuleFinishedBinding

/**
 * Final fragment for voice recorder module.
 *
 * @author Matthew Crossman
 */
class VoiceRecorderFinishedFragment : Fragment() {

    private lateinit var binding: FragmentVoiceRecorderModuleFinishedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_voice_recorder_module_finished,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.voiceRecorderFinishButton.setOnClickListener {
            finishLesson()
        }
    }

    /**
     * Performs end-of-lesson actions.
     */
    private fun finishLesson() {
        activity?.finish()
    }

}