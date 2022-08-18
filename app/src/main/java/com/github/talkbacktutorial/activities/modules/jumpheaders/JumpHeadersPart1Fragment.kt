package com.github.talkbacktutorial.activities.modules.jumpheaders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentJumpHeadersModulePart1Binding

/**
 * A fragment holding some introductory text displayed in the common jump navigation fragment.
 *
 * @author Matthew Crossman
 * @see com.github.talkbacktutorial.activities.modules.jumpnavigation.JumpNavigationPart1Fragment
 */
class JumpHeadersPart1Fragment : Fragment() {

    lateinit var binding: FragmentJumpHeadersModulePart1Binding
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_jump_headers_module_part1,
            container,
            false
        )
        return binding.root
    }
}
