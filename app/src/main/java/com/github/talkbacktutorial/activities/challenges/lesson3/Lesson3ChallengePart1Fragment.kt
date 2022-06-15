package com.github.talkbacktutorial.activities.challenges.lesson3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentLesson3ChallengePart1Binding
import java.util.*

class Lesson3ChallengePart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = Lesson3ChallengePart1Fragment()
    }

    private lateinit var binding: FragmentLesson3ChallengePart1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson3_challenge_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // set accessibility delegate/interceptor
        view.accessibilityDelegate = InterceptorDelegate(this.binding)

        // interactive targets
        val firstTarget: View
        val secondTarget: View
        val thirdTarget: View

        // strings and labels
        val promptTemplate = getString(R.string.jump_navigation_easy_prompt)

        // switch strings & layout targets according to mode
        firstTarget = binding.challenge.targetHeader
        secondTarget = binding.challenge.target2Link
        thirdTarget = binding.challenge.target3Control

        val headerLabel: String = getString(R.string.jump_headers_entity)
        val headerLabelPlural: String = getString(R.string.jump_headers_entity_plural)
        val linkLabel: String = getString(R.string.jump_links_entity)
        val linkLabelPlural: String = getString(R.string.jump_links_entity_plural)
        val controlLabel: String = getString(R.string.jump_controls_entity)
        val controlLabelPlural: String = getString(R.string.jump_controls_entity_plural)

        // set up targets
        val targets = ArrayList(
            listOf(
                firstTarget, secondTarget, thirdTarget
            )
        )

        targets.forEach {
            it.visibility = View.VISIBLE

            // header must be explicitly enabled
            ViewCompat.setAccessibilityHeading(it, true)

            binding.challenge.prompt.text =
                String.format(promptTemplate, headerLabel, headerLabelPlural)
            binding.challenge.prompt2.text =
                String.format(promptTemplate, linkLabel, linkLabelPlural)
            binding.challenge.prompt3.text =
                String.format(promptTemplate, controlLabel, controlLabelPlural)

            val targetTemplate = getString(R.string.jump_navigation_target_link_template)

            // set up target strings
            val secondTargetTemplate =
                String.format(targetTemplate, getString(R.string.jump_navigation_hard_second_element))

            // set up button text
            firstTarget.text =
                String.format(getString(R.string.jump_navigation_hard_first_element), headerLabel)
            thirdTarget.text =
                String.format(getString(R.string.jump_navigation_hard_last_element), controlLabel)

            val secondTargetTextContent = String.format(secondTargetTemplate, linkLabel)

            val secondTargetLink =
                HtmlCompat.fromHtml(secondTargetTextContent, HtmlCompat.FROM_HTML_MODE_COMPACT)

            secondTarget.text = secondTargetLink

            thirdTarget.setOnClickListener {
                    updateModule()
                    activity?.finish()
            }
            super.onViewCreated(view, savedInstanceState)
        }
    }

    /**
     * Intercepts focus events to move your position if you're on a pushback element
     * @author Emmanuel Chu
     */
    private class InterceptorDelegate(val binding: FragmentLesson3ChallengePart1Binding) :
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
     * Updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule(){
        val moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }
}