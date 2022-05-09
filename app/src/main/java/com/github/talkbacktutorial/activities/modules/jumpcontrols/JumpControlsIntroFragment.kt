package com.github.talkbacktutorial.activities.modules.jumpcontrols

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentJumpControlsIntroTextBinding

/**
 * Introduction fragment telling user how to switch to controls-mode and gives them only one target
 * to jump to at the end of the layout.
 *
 * @author Matthew Crossman
 */
class JumpControlsIntroFragment : Fragment() {
    private lateinit var binding: FragmentJumpControlsIntroTextBinding

    // To others: please don't put TTS here.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_jump_controls_intro_text,
            container,
            false
        )
        return binding.root
    }

}