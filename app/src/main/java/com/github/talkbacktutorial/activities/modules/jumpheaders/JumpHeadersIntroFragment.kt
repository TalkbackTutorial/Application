package com.github.talkbacktutorial.activities.modules.jumpheaders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentJumpHeadersIntroTextBinding

/**
 * Fragment to show the introduction to the jumping headers module.
 *
 * @author Matthew Crossman
 */
class JumpHeadersIntroFragment : Fragment() {
    lateinit var binding: FragmentJumpHeadersIntroTextBinding
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_jump_headers_intro_text,
            container,
            false
        )
        return binding.root
    }
}