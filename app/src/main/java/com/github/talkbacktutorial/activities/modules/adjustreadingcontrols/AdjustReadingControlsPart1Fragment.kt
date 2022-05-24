package com.github.talkbacktutorial.activities.modules.adjustreadingcontrols

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentAdjustReadingControlsModulePart1Binding

class AdjustReadingControlsPart1Fragment : Fragment() {

    private lateinit var binding: FragmentAdjustReadingControlsModulePart1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_adjust_reading_controls_module_part1,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // show the multi-finger info if on a version of android that supports them
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P)
            binding.multiFingerPrompt.visibility = View.GONE

        // makes button leave the fragment
        binding.finishModule.setOnClickListener {
            updateModule()
            activity?.onBackPressed()
        }
    }

    /**
     * This method updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule(){
        val moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }
}
