package com.github.talkbacktutorial.activities.modules.jumplinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentJumpLinksIntroTextBinding

/**
 * Introductory information for learning about how to jump between links.
 *
 * @author Matthew Crossman
 */
class JumpLinksIntroFragment : Fragment() {
    lateinit var binding: FragmentJumpLinksIntroTextBinding
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_jump_links_intro_text,
            container,
            false
        )
        return binding.root
    }
}