package com.github.talkbacktutorial.activities.modules.jumpcontrols

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.TextView
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
        view.accessibilityDelegate = InterceptorDelegate()
        super.onViewCreated(view, savedInstanceState)
    }

    private class InterceptorDelegate : View.AccessibilityDelegate() {
        override fun onRequestSendAccessibilityEvent(
            host: ViewGroup?,
            child: View?,
            event: AccessibilityEvent?
        ): Boolean {
            if (event?.eventType == AccessibilityEvent.TYPE_VIEW_FOCUSED) {
                if (child?.id == R.id.trap_text) {
                    val wrongActionText = host?.findViewById<TextView>(R.id.wrong_action_message)
                    wrongActionText?.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                }
            }

            return super.onRequestSendAccessibilityEvent(host, child, event)
        }
    }

}