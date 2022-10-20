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
import com.github.talkbacktutorial.databinding.FragmentJumpTextModulePart4Binding

class JumpTextPart4Fragment : Fragment() {

    private lateinit var binding: FragmentJumpTextModulePart4Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_jump_text_module_part4, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.targetWord.setOnClickListener {
            this.onClickContinueLesson()
        }
        // fix TalkBack putting focus at end of fragment
        binding.layout[0].sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
    }

    /**
     * Directs user to the next fragment of the module
     * @author Joel Yang
     */
    private fun onClickContinueLesson() {
        updateModule()
        parentFragmentManager.commit {
            replace(this@JumpTextPart4Fragment.id, JumpTextPart5Fragment())
        }
    }

    override fun onDestroyView() {
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