package com.github.talkbacktutorial.activities.modules.goback

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.core.view.allViews
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.activities.lesson0.Lesson0Activity
import com.github.talkbacktutorial.activities.lesson0.Lesson0Part2Fragment
import com.github.talkbacktutorial.activities.modules.scrolling.ScrollingModuleActivity
import com.github.talkbacktutorial.databinding.FragmentGobackModulePart1Binding
import com.github.talkbacktutorial.databinding.PillButtonBinding

class GoBackModulePart1Fragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = GoBackModulePart1Fragment()
    }

    private lateinit var binding: FragmentGobackModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_goback_module_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ttsEngine = TextToSpeechEngine((activity as GoBackModuleActivity))
            .onFinishedSpeaking(triggerOnce = true) {
                binding.continueButton.button.visibility = View.VISIBLE
            }
        this.setupContinueButton()
        this.setupFinishLessonButton();
        this.speakIntro()
    }

    override fun onResume() {
        super.onResume()
            this.ttsEngine = TextToSpeechEngine((activity as GoBackModuleActivity))
                .onFinishedSpeaking(triggerOnce = true) {
                    binding.finishLessonButton.button.visibility = View.VISIBLE
                }
            this.setupFinishLessonButton();
            this.speakConclusion();
    }

    /**
     * Sets up the continue button's visibility and listener.
     * @author Emmanuel Chu
     */
    private fun setupContinueButton() {
        // The button starts off invisible
        binding.continueButton.button.visibility = View.GONE
        // The button transitions to the next fragment when clicked
        binding.continueButton.button.setOnClickListener {
            parentFragmentManager.commit {
                replace(this@GoBackModulePart1Fragment.id, GoBackModulePart2Fragment.newInstance())
                addToBackStack("gobackModulePart2")
            }
        }
    }

    /**
     * Inserts a button to finish the lesson.
     * @author Emmanuel Chu
     */
    private fun setupFinishLessonButton() {
        val primaryButtonBinding: PillButtonBinding = binding.finishLessonButton
        binding.finishLessonButton.button.visibility = View.GONE  // the button starts off invisible
        primaryButtonBinding.text = "Finish Lesson"
        primaryButtonBinding.button.setOnClickListener {
            this.finishLesson()
        }
    }

    /**
     * Speaks an intro for the fragment.
     * @author Emmanuel Chu
     */
    private fun speakIntro() {
        val intro = """
            Welcome.
            In this lesson, you'll learn how to go back the previous page.
            Double tap to continue.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks an intro for the fragment.
     * @author Emmanuel Chu
     */
    private fun speakConclusion() {
        val conclusion = """
            You have successfully navigated back to the previous page.
            Double tap to complete lesson.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(conclusion)
    }

    /**
     * Announced the lesson's completion then returns the user back to the lessons Activity.
     * @author Emmanuel Chu
     */
    private fun finishLesson() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent = Intent((activity as Lesson0Activity), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        this.ttsEngine.speak("You have completed the lesson. Sending you to the main menu.", override = true)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
    }












}