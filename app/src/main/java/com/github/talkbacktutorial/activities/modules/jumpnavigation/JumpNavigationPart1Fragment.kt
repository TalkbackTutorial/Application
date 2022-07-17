package com.github.talkbacktutorial.activities.modules.jumpnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.jumpcontrols.JumpControlsPart1Fragment
import com.github.talkbacktutorial.activities.modules.jumpheaders.JumpHeadersPart1Fragment
import com.github.talkbacktutorial.activities.modules.jumplinks.JumpLinksPart1Fragment
import com.github.talkbacktutorial.databinding.FragmentJumpNavigationModulePart1Binding

/**
 * A common "part 1" or introduction fragment for all jump navigation modules (links, headers, and
 * controls). The mode must be set in order for it to work.
 *
 * @author Matthew Crossman
 */
class JumpNavigationPart1Fragment(@NonNull private val mode: NavigationMode) : Fragment() {

    private lateinit var binding: FragmentJumpNavigationModulePart1Binding
    private val isLastFragment = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_jump_navigation_module_part1,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // set accessibility delegate/interceptor
        view.accessibilityDelegate = InterceptorDelegate(this.binding)

        // interactive targets
        val firstTarget: View
        val lastTarget: View

        // strings and labels
        val entityLabel: String
        val entityLabelPlural: String
        val promptTemplate = getString(R.string.jump_navigation_easy_prompt)

        // intro fragment
        val introFragment: Fragment

        // switch strings & layout targets according to mode
        when (mode) {
            NavigationMode.HEADERS -> {
                firstTarget = binding.challenge.targetHeader
                lastTarget = binding.challenge.target2Header

                entityLabel = getString(R.string.jump_headers_entity)
                entityLabelPlural = getString(R.string.jump_headers_entity_plural)

                introFragment = JumpHeadersPart1Fragment()
            }
            NavigationMode.LINKS -> {
                firstTarget = binding.challenge.targetLink
                lastTarget = binding.challenge.target2Link

                entityLabel = getString(R.string.jump_links_entity)
                entityLabelPlural = getString(R.string.jump_links_entity_plural)

                introFragment = JumpLinksPart1Fragment()
            }
            NavigationMode.CONTROLS -> {
                firstTarget = binding.challenge.targetControl
                lastTarget = binding.challenge.target2Control

                entityLabel = getString(R.string.jump_controls_entity)
                entityLabelPlural = getString(R.string.jump_controls_entity_plural)

                introFragment = JumpControlsPart1Fragment()
            }
        }

        // attach appropriate intro fragment
        childFragmentManager.commit {
            replace(R.id.intro_text, introFragment)
        }

        // set up targets
        val targets = ArrayList(
            listOf(
                firstTarget, lastTarget
            )
        )

        targets.forEach {
            it.visibility = View.VISIBLE

            // header must be explicitly enabled
            if (mode == NavigationMode.HEADERS)
                ViewCompat.setAccessibilityHeading(it, true)
        }

        binding.challenge.prompt.text =
            String.format(promptTemplate, entityLabelPlural, entityLabel)

        // templating the target strings
        val targetTemplate =
            if (mode == NavigationMode.LINKS) getString(R.string.jump_navigation_target_link_template) else getString(R.string.percent_s)

        // set up target strings
        val firstTargetTemplate =
            String.format(targetTemplate, getString(R.string.jump_navigation_easy_first_element))
        val firstTargetActiveTemplate = String.format(
            targetTemplate,
            getString(R.string.jump_navigation_easy_first_element_active)
        )

        val lastTargetTemplate =
            String.format(targetTemplate, getString(R.string.jump_navigation_easy_last_element))
        val lastTargetActiveTemplate = String.format(
            targetTemplate,
            getString(R.string.jump_navigation_easy_last_element_active)
        )

        // text contents
        val firstTargetActiveActionText =
            if (isLastFragment) getString(R.string.final_button_end_module_prompt) else getString(R.string.final_button_mid_module_prompt)

        // links vs regular text
        val firstTargetTextContent = String.format(firstTargetTemplate, entityLabel)
        val lastTargetTextContent = String.format(lastTargetTemplate, entityLabel)
        val firstTargetActiveTextContent =
            String.format(firstTargetActiveTemplate, entityLabel, firstTargetActiveActionText)
        val lastTargetActiveTextContent = String.format(lastTargetActiveTemplate, entityLabel)

        val firstTargetLink =
            HtmlCompat.fromHtml(firstTargetTextContent, HtmlCompat.FROM_HTML_MODE_COMPACT)
        val lastTargetLink =
            HtmlCompat.fromHtml(lastTargetTextContent, HtmlCompat.FROM_HTML_MODE_COMPACT)
        val firstTargetActiveLink =
            HtmlCompat.fromHtml(firstTargetActiveTextContent, HtmlCompat.FROM_HTML_MODE_COMPACT)
        val lastTargetActiveLink =
            HtmlCompat.fromHtml(lastTargetActiveTextContent, HtmlCompat.FROM_HTML_MODE_COMPACT)

        // start editing targets
        val firstTargetText = when (mode) {
            NavigationMode.CONTROLS -> firstTarget as Button
            else -> firstTarget as TextView
        }

        val lastTargetText = when (mode) {
            NavigationMode.CONTROLS -> lastTarget as Button
            else -> lastTarget as TextView
        }

        // set target text
        if (mode == NavigationMode.LINKS) {
            firstTargetText.text = firstTargetLink
            lastTargetText.text = lastTargetLink
        } else {
            firstTargetText.text = firstTargetTextContent
            lastTargetText.text = lastTargetTextContent
        }

        // make targets usable
        lastTarget.setOnClickListener {
            // turn first button into finish module button
            if (mode == NavigationMode.LINKS)
                firstTargetText.text = firstTargetActiveLink
            else
                firstTargetText.text = firstTargetActiveTextContent

            targets.first().setOnClickListener {
                activity?.finish()
            }

            // change last button text & remove listener
            if (mode == NavigationMode.LINKS)
                lastTargetText.text = lastTargetActiveLink
            else
                lastTargetText.text = lastTargetActiveTextContent

            lastTarget.setOnClickListener(null)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Intercepts focus events to move your position if you're on a pushback element
     */
    private class InterceptorDelegate(val binding: FragmentJumpNavigationModulePart1Binding) :
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
