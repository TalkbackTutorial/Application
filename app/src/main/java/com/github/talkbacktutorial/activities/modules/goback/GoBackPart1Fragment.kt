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
            this.speakConclusion()
            this.finishLesson()
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
                addToBackStack(getString(R.string.go_back_part2_backstack))
            }
        }
    }


    /**
     * Speaks an intro for the fragment.
     * @author Emmanuel Chu
     */
    private fun speakIntro() {
        val intro = getString(R.string.go_back_part1_intro).trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks an intro for the fragment.
     * @author Emmanuel Chu
     */
    private fun speakConclusion() {
        val conclusion = getString(R.string.go_back_part1_outro).trimIndent()
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
