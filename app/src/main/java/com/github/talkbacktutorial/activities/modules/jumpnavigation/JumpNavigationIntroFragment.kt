package com.github.talkbacktutorial.activities.modules.jumpnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.jumpcontrols.JumpControlsIntroFragment
import com.github.talkbacktutorial.databinding.FragmentJumpNavigationIntroBinding

/**
 * Creates a generic introduction fragment for all jump modules.
 *
 * @author Matthew Crossman
 */
class JumpNavigationIntroFragment(private val mode: NavigationMode) : Fragment() {
    private lateinit var binding: FragmentJumpNavigationIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_jump_navigation_intro,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // set accessibility delegate/interceptor
        view.accessibilityDelegate = InterceptorDelegate(this.binding)

        // attach appropriate intro fragment
        childFragmentManager.commit {
            replace(R.id.intro_text, JumpControlsIntroFragment())
        }

        val firstTarget : View
        val lastTarget : View

        when (mode) {
            NavigationMode.HEADERS -> {
                firstTarget = binding.challenge.targetControl
                lastTarget = binding.challenge.target2Control
            }
            NavigationMode.LINKS -> {
                firstTarget = binding.challenge.targetLink
                lastTarget = binding.challenge.target2Link
            }
            NavigationMode.CONTROLS -> {
                firstTarget = binding.challenge.targetControl
                lastTarget = binding.challenge.target2Control
            }
        }

        val targets = ArrayList(listOf(
            firstTarget, lastTarget
        ))

        targets.forEach {
            it.visibility = View.VISIBLE

            // set header
            if (mode == NavigationMode.HEADERS)
                ViewCompat.setAccessibilityHeading(it, true)
        }

        // set up text fields
        // for prompt
        val promptString = when (mode) {
            NavigationMode.HEADERS -> getString(R.string.jump_controls_intro_first_prompt)
            NavigationMode.LINKS -> getString(R.string.jump_controls_intro_first_prompt)
            NavigationMode.CONTROLS -> getString(R.string.jump_controls_intro_first_prompt)
        }

        binding.challenge.prompt.text = promptString

        // for buttons
        val entityLabel = when (mode) {
            NavigationMode.HEADERS -> getString(R.string.jump_headers_entity)
            NavigationMode.LINKS -> getString(R.string.jump_links_entity)
            NavigationMode.CONTROLS -> getString(R.string.jump_controls_entity)
        }

        val entitiesLabel = when (mode) {
            NavigationMode.HEADERS -> getString(R.string.jump_headers_entity_plural)
            NavigationMode.LINKS -> getString(R.string.jump_links_entity_plural)
            NavigationMode.CONTROLS -> getString(R.string.jump_controls_entity_plural)
        }

        // update cards
        val firstTargetText = when (mode) {
            NavigationMode.CONTROLS -> firstTarget as Button
            else -> firstTarget as TextView
        }

        val lastTargetText = when (mode) {
            NavigationMode.CONTROLS -> lastTarget as Button
            else -> lastTarget as TextView
        }

        firstTargetText.text = String.format(getString(R.string.jump_navigation_easy_first_element), entityLabel)
        lastTargetText.text = String.format(getString(R.string.jump_navigation_easy_last_element), entityLabel)

        // set up cards as buttons
        lastTarget.setOnClickListener {
            // turn first button into finish module button

            firstTargetText.text = String.format(getString(R.string.jump_navigation_easy_first_element_active), entitiesLabel, getString(R.string.finish_module_button))

            targets.first().setOnClickListener {
                activity?.finish()
            }

            // change last button text & remove listener
            lastTargetText.text = String.format(getString(R.string.jump_navigation_easy_last_element_active), entityLabel)
            lastTarget.setOnClickListener(null)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Intercepts focus events to move your position if you're on a pushback element
     */
    private class InterceptorDelegate(val binding: FragmentJumpNavigationIntroBinding) :
        View.AccessibilityDelegate() {

        override fun onRequestSendAccessibilityEvent(
            host: ViewGroup?,
            child: View?,
            event: AccessibilityEvent?
        ): Boolean {
            if (event?.eventType == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                // make pushbacks move you to text view with a message
                if (binding.challenge.pushbackText.isAccessibilityFocused)
                    binding.challenge.wrongActionText.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                if (binding.challenge.pushbackText2.isAccessibilityFocused)
                    binding.challenge.wrongActionText2.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
            }

            return super.onRequestSendAccessibilityEvent(host, child, event)
        }
    }

    /**
     * Enumerates over navigation modes
     */
    enum class NavigationMode {
        HEADERS, LINKS, CONTROLS
    }
}