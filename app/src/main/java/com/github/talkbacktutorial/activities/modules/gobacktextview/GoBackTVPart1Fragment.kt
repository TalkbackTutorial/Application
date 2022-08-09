package com.github.talkbacktutorial.activities.modules.gobacktextview

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
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentGobacktvModulePart1Binding
import com.github.talkbacktutorial.databinding.PillButtonBinding

class GoBackTVPart1Fragment : Fragment() {

    companion object {
        var returning: Boolean = false

        @JvmStatic
        fun newInstance() = GoBackTVPart1Fragment()
    }

    private lateinit var binding: FragmentGobacktvModulePart1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gobacktv_module_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!returning) {
            binding.finishLessonButton.button.visibility = View.GONE
            this.setupContinueButton()
        } else if (returning) {
            binding.continueButton.button.visibility = View.GONE
            binding.goBackTvIntro.visibility = View.GONE
            binding.finishLessonButton.button.visibility = View.VISIBLE
            binding.goBackTvOutro.visibility = View.VISIBLE
            this.setupFinishLessonButton()
        }
    }

    /**
     * Sets up the continue button's visibility and listener.
     * @author Emmanuel Chu
     */
    private fun setupContinueButton() {
        // The button transitions to the next fragment when clicked
        binding.continueButton.button.setOnClickListener {
            parentFragmentManager.commit {
                replace(this@GoBackTVPart1Fragment.id, GoBackTVPart2Fragment.newInstance())
                addToBackStack(getString(R.string.go_back_part2_backstack))
            }
        }
    }

    /**
     * Inserts a button to finish the lesson.
     * @author Emmanuel Chu
     */
    private fun setupFinishLessonButton() {
        val primaryButtonBinding: PillButtonBinding = binding.finishLessonButton
        primaryButtonBinding.text = getString(R.string.finish_lesson)
        primaryButtonBinding.button.setOnClickListener {
            this.finishLesson()
        }
    }

    /**
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Emmanuel Chu
     */
    private fun finishLesson() {
        updateModule()
        returning = false
        activity?.finish()
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
