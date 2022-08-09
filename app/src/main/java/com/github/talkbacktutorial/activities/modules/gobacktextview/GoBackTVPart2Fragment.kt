package com.github.talkbacktutorial.activities.modules.gobacktextview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentGobacktvModulePart2Binding

class GoBackTVPart2Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = GoBackTVPart2Fragment()
    }

    private lateinit var binding: FragmentGobacktvModulePart2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gobacktv_module_part2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GoBackTVPart1Fragment.returning = true
    }

}
