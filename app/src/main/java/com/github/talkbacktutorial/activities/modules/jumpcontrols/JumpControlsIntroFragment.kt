package com.github.talkbacktutorial.activities.modules.jumpcontrols

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
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

        // set up last button click action
        binding.lastButton.setOnClickListener {
            // turn first button into finish module button
            binding.firstButton.setText(R.string.jump_controls_intro_first_alt_button)
            binding.firstButton.setOnClickListener {
                activity?.finish()
            }

            // change last button text & remove listener
            if (it is Button) {
                it.setText(R.string.jump_controls_intro_last_alt_button)
                it.setOnClickListener(null)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Intercepts focus events to move your position if you're on a pushback element
     */
    private class InterceptorDelegate(val binding: FragmentJumpControlsIntroBinding) :
        View.AccessibilityDelegate() {

        override fun onRequestSendAccessibilityEvent(
            host: ViewGroup?,
            child: View?,
            event: AccessibilityEvent?
        ): Boolean {
            if (event?.eventType == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                // make pushbacks move you to text view with a message
                if (binding.pushback.isAccessibilityFocused)
                    binding.wrongActionMessage.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                if (binding.pushback2.isAccessibilityFocused)
                    binding.wrongActionMessage2.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
            }

            return super.onRequestSendAccessibilityEvent(host, child, event)
        }
    }

}