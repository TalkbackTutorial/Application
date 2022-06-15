package com.github.talkbacktutorial.activities.modules.goback

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.LessonActivity
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.FragmentGobackModulePart1Binding
import com.github.talkbacktutorial.databinding.PillButtonBinding
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer

class GoBackPart1Fragment : Fragment() {

    companion object {
        var returning: Boolean = false

        @JvmStatic
        fun newInstance() = GoBackPart1Fragment()
    }

    private lateinit var binding: FragmentGobackModulePart1Binding
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_goback_module_part1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.finishLessonButton.button.visibility = View.GONE
        binding.continueButton.button.visibility = View.GONE
        if (!returning) {
            this.ttsEngine = TextToSpeechEngine((activity as GoBackActivity))
                .onFinishedSpeaking(triggerOnce = true) {
                    binding.continueButton.button.visibility = View.VISIBLE
                }
            this.setupContinueButton()
            this.speakIntro()
        } else if (returning) {
            this.ttsEngine = TextToSpeechEngine((activity as GoBackActivity))
                .onFinishedSpeaking(triggerOnce = true) {
                    binding.finishLessonButton.button.visibility = View.VISIBLE
                }
            this.setupFinishLessonButton()
            this.speakConclusion()
        }
    }

    /**
     * Sets up the continue button's visibility and listener.
     * @author Emmanuel Chu
     */
    private fun setupContinueButton() {
        // The button transitions to the next fragment when clicked
        binding.continueButton.button.setOnClickListener {
            parentFragmentManager.commit {
                replace(this@GoBackPart1Fragment.id, GoBackPart2Fragment.newInstance())
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

        updateModule()

        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent = Intent((activity as GoBackActivity), LessonActivity::class.java)
            val currentLesson : Lesson = LessonContainer.getAllLessons()[1]
            intent.putExtra(Lesson.INTENT_KEY, currentLesson.id.toString())
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            returning = false
        }
        this.ttsEngine.speak("You have completed the lesson. Sending you to the main menu.", override = true)
    }

    override fun onDestroyView() {
        this.ttsEngine.shutDown()
        super.onDestroyView()
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
