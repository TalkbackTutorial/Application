package com.github.talkbacktutorial.activities.challenges.lesson3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.jumpheaders.JumpHeadersPart1Fragment
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
    private val isLastFragment = true

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
//
//        // intro fragment
//        val introFragment: Fragment

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

//        introFragment = JumpHeadersPart1Fragment()


        // attach appropriate intro fragment
//        childFragmentManager.commit {
//            replace(R.id.intro_text, introFragment)
//        }

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

            // update cards

            // set up button text
            firstTarget.text =
                String.format(getString(R.string.jump_navigation_hard_first_element), headerLabel)
            secondTarget.text =
                String.format(getString(R.string.jump_navigation_hard_second_element), linkLabel)
            thirdTarget.text =
                String.format(getString(R.string.jump_navigation_hard_last_element), controlLabel)

            val firstTargetActiveActionText =
                if (isLastFragment) getString(R.string.final_button_end_module_prompt) else getString(
                    R.string.final_button_mid_module_prompt
                )

//            val firstTargetActiveText = String.format(
//                getString(R.string.jump_navigation_easy_first_element_active),
//                entityLabelPlural,
//                firstTargetActiveActionText
//            )

//            val lastTargetActiveText =
//                String.format(
//                    getString(R.string.jump_navigation_easy_last_element_active),
//                    entityLabel
//                )

            // set up cards as buttons
            thirdTarget.setOnClickListener {
                // turn first button into finish module button
//                firstTarget.text = firstTargetActiveText

                targets.first().setOnClickListener {
                    updateModule()
                    activity?.finish()
                }

                // change last button text & remove listener
//                secondTarget.text = lastTargetActiveText
                thirdTarget.setOnClickListener(null)
            }

            super.onViewCreated(view, savedInstanceState)
    }







}

    /**
     * Enumerates over navigation modes
     */
    enum class NavigationMode {
        HEADERS, LINKS, CONTROLS
    }

    /**
     * Intercepts focus events to move your position if you're on a pushback element
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
     * This method updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule(){
        val moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }


//    private lateinit var binding: FragmentLesson3ChallengePart1Binding
//    private lateinit var ttsEngine: TextToSpeechEngine
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson5_challenge_part1, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        this.ttsEngine = TextToSpeechEngine((activity as Lesson5ChallengeActivity))
//            .onFinishedSpeaking(triggerOnce = true) {
//            }
//        this.speakIntro()
//    }

}