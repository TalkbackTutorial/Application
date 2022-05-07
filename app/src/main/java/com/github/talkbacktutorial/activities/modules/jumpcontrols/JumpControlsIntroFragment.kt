package com.github.talkbacktutorial.activities.modules.jumpcontrols

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.FragmentJumpControlsIntroBinding

/**
 * Introduction fragment telling user how to switch to controls-mode and gives them only one target
 * to jump to at the end of the layout.
 *
 * @author Matthew Crossman
 */
class JumpControlsIntroFragment : Fragment() {
    private lateinit var binding: FragmentJumpControlsIntroBinding

    // To others: please don't put TTS here.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_jump_controls_intro,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // set accessibility delegate/interceptor
        view.accessibilityDelegate = InterceptorDelegate(this.binding)

        // set button action to finish activity
        binding.successButton.setOnClickListener {
            activity?.finish()
        }

        binding.pushback.accessibilityClassName

        super.onViewCreated(view, savedInstanceState)
    }

    private class InterceptorDelegate(val binding: FragmentJumpControlsIntroBinding) :
        View.AccessibilityDelegate() {

        override fun onRequestSendAccessibilityEvent(
            host: ViewGroup?,
            child: View?,
            event: AccessibilityEvent?
        ): Boolean {
            if (event?.eventType == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                if (binding.pushback.isAccessibilityFocused)
                    binding.wrongActionMessage.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
            }

            return super.onRequestSendAccessibilityEvent(host, child, event)
        }
    }

}