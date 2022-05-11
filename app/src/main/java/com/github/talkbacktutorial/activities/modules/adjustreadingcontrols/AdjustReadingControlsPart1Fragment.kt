package com.github.talkbacktutorial.activities.modules.adjustreadingcontrols

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
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
            activity?.onBackPressed()
        }
    }
}
