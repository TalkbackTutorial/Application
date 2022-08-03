package com.github.talkbacktutorial.activities.modules.jumptext

import android.content.Context
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
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentJumpTextModulePart3Binding

class JumpTextPart3Fragment : Fragment() {

    private lateinit var binding: FragmentJumpTextModulePart3Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    companion object {
        @JvmStatic
        fun newInstance() = JumpTextPart3Fragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_jump_text_module_part3, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as JumpTextActivity))
        this.speakIntro()
        binding.continueText.setOnClickListener {
            this.onClickContinueLesson()
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Joel Yang
     */
    private fun speakIntro() {
        val intro = getString(R.string.jump_text_paragraphs_intro2).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Directs user to the next fragment of the module
     * @author Joel Yang
     */
    private fun onClickContinueLesson() {
        updateModule()
        parentFragmentManager.commit {
            replace(this@JumpTextPart3Fragment.id, JumpTextPart4Fragment.newInstance())
        }
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
