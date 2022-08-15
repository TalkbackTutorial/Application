package com.github.talkbacktutorial.activities.modules.jumptext

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentJumpTextModulePart5Binding

class JumpTextPart5Fragment : Fragment() {

    private lateinit var binding: FragmentJumpTextModulePart5Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_jump_text_module_part5,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as JumpTextActivity))
        setupTextViewTransition()
        this.speakIntro()
        binding.jumpCharsBlock2.setOnClickListener {
            this.onClickContinueLesson()
        }
        // fix TalkBack putting focus at end of fragment
        binding.layout[0].sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
    }

    private fun setupTextViewTransition() {
        // The views starts off invisible
        binding.jumpCharsBlock1.visibility = View.GONE
        binding.jumpCharsBlock2.visibility = View.GONE
        binding.jumpCharsBlock3.visibility = View.GONE

        // enable views after tts engine intro
        this.ttsEngine = TextToSpeechEngine((activity as JumpTextActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                binding.jumpCharsBlock1.visibility = View.VISIBLE
                binding.jumpCharsBlock2.visibility = View.VISIBLE
                binding.jumpCharsBlock3.visibility = View.VISIBLE
            }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Joel Yang
     */
    private fun speakIntro() {
        val intro = getString(R.string.jump_text_paragraphs_intro4).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Directs user back to lesson 3 modules page
     * @author Joel Yang
     */
    private fun onClickContinueLesson() {
        updateModule()
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            activity?.finish()
        }
        this.ttsEngine.speak(getString(R.string.jump_text_paragraphs_conclusion), override = true)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }

    /**
     * Updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule() {
        val moduleProgressionViewModel =
            ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }
}