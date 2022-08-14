package com.github.talkbacktutorial.activities.modules.openingvoicecommand

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.LessonActivity
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentOpenVoiceCommandModulePart2Binding
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer

class OpenVoiceCommandPart2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OpenVoiceCommandPart2Fragment()
    }

    private lateinit var binding: FragmentOpenVoiceCommandModulePart2Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_open_voice_command_module_part2, container, false)
        binding.openVoiceCommandText.text = getString(R.string.open_voice_commands_part2_text)
        binding.openVoiceCommandText.visibility = View.GONE

        binding.openVoiceCommandText2.text = getString(R.string.open_voice_command_part2_text_end)
        binding.openVoiceCommandText2.visibility = View.GONE

        binding.controlText.text = getString(R.string.open_voice_command_part2_control_text)
        binding.controlText.visibility = View.GONE

        binding.openVoiceCommandButton.text = getString(R.string.finish_module_button)
        binding.openVoiceCommandButton.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as OpenVoiceCommandActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                binding.openVoiceCommandText.visibility = View.VISIBLE
                binding.openVoiceCommandText2.visibility = View.VISIBLE
                binding.controlText.visibility = View.VISIBLE
                binding.openVoiceCommandButton.visibility = View.VISIBLE
            }
        this.speakIntro()
        this.setupButton()
    }

    /**
     * Setups button for completing the module
     * @author Jai Clapp
     */
    private fun setupButton() {
        binding.openVoiceCommandButton.setOnClickListener {
            parentFragmentManager.commit {
                finishLesson()
            }
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Jai Clapp
     */
    private fun speakIntro() {
        val intro = getString(R.string.open_voice_commands_part2_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Announces lesson completion when the lesson is finished and sends back to lesson screen
     * @author Jai Clapp
     */
    private fun finishLesson() {
        updateModule()

        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            activity?.finish()
        }
        val outro = getString(R.string.open_voice_commands_part2_outro).trimIndent()
        ttsEngine.speak(outro)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * Updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule(){
        val moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }
}