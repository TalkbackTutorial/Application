package com.github.talkbacktutorial.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.database.LessonProgression
import com.github.talkbacktutorial.database.LessonProgressionViewModel
import com.github.talkbacktutorial.databinding.*
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer
import com.github.talkbacktutorial.lessons.modules.Module

class LessonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLessonBinding
    private lateinit var lesson: Lesson

    private lateinit var lessonProgressionViewModel: LessonProgressionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_lesson)

        this.intent.getStringExtra(Lesson.INTENT_KEY)?.let { id ->
            this.lesson = LessonContainer.getLesson(id)
            binding.lesson = this.lesson
            this.displayModules()
        }
    }
    /*
    /**
     * Adds a module card for each module the lesson holds, which starts the respective
     * module when clicked.
     * @author Andre Pham
     */
    private fun setupModules() {
        // TODO: database integration, only display modules that have been completed and the next module
        for (module in this.lesson.modules) {
            val moduleCardBinding: ModuleCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.module_card, binding.modulesLinearLayout, false
            )
            moduleCardBinding.title = module.title
            moduleCardBinding.subtitle = getString(
                R.string.module_subtitle,
                this.lesson.getModuleSequenceNumeral(module)
            )
            moduleCardBinding.moduleCard.setOnClickListener {
                module.startActivity(this)
            }
            binding.modulesLinearLayout.addView(moduleCardBinding.moduleCard)
        }
    }
    */

    /**
     * Pulls the required lessonProgression item from the database to determine which modules should be
     * locked.
     * @author Jade Davis
     */
    private fun displayModules() {
        lessonProgressionViewModel = ViewModelProvider(this).get(LessonProgressionViewModel::class.java)
        lessonProgressionViewModel.getAllLessonProgressions.observe(this) {lessons ->
            if (lessons.isEmpty()) {
                lessonProgressionViewModel.fillDatabase()
            } else {
                displayModules(lessons[lesson.sequenceNumeral])
            }
        }
    }

    /**
     * Loads all modules in the lesson, and sets each as locked or unlocked depending on the
     * user's progression.
     * @param lessonProgression A database entry which specifies the number of modules completed
     * @author Jade Davis
     */
    private fun displayModules(lessonProgression: LessonProgression) {
        var moduleCount = 0
        val modules = this.lesson.modules

        do {
            loadModuleCard(modules[moduleCount], locked = false)
            moduleCount++
        } while (moduleCount <= lessonProgression.modulesCompleted)

        for (i in moduleCount until modules.size) {
            loadModuleCard(modules[i], locked = true)
        }

        if (modules.size - 1 == lessonProgression.modulesCompleted) {
            this.setupChallenge(locked = false)
        } else {
            this.setupChallenge(locked = true)
        }
    }

    /**
     * Loads a card for each module in the lesson, both unlocked and locked.
     * @param module Module which is to be loaded into a new card
     * @param locked Boolean which determines whether the module is locked or not
     * @author Jade Davis
     */
    private fun loadModuleCard(module: Module, locked: Boolean) {
        val moduleCardBinding: ModuleCardBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.module_card, binding.modulesLinearLayout, false
        )
        moduleCardBinding.title = module.title
        moduleCardBinding.subtitle = getString(
            R.string.module_subtitle,
            this.lesson.getModuleSequenceNumeral(module)
        )

        if (!locked) {
            moduleCardBinding.moduleCard.setOnClickListener {
                module.startActivity(this)
            }
        } else {
            moduleCardBinding.title = "Locked - " + module.title
            moduleCardBinding.moduleTitle.alpha = 0.5f
            moduleCardBinding.moduleTitle.alpha = 0.5f
        }

        binding.modulesLinearLayout.addView(moduleCardBinding.moduleCard)
    }

    /**
     * Adds a challenge card if the lesson has a challenge. Starts the challenge when clicked.
     * @param locked Boolean which determines whether the module is locked or not
     * @author Andre Pham, updated by Jade Davis
     */
    private fun setupChallenge(locked: Boolean) {
        val challenge = this.lesson.challenge
        if (challenge != null) {
            val challengeCardBinding: ChallengeCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.challenge_card, binding.modulesLinearLayout, false
            )
            challengeCardBinding.subtitle = getString(
                R.string.module_subtitle,
                this.lesson.modules.size + 1
            )

            if (!locked) {
                challengeCardBinding.card.setOnClickListener {
                    challenge.startActivity(this)
                }
            } else {
                // TODO: find where challenge title is pulled from
                challengeCardBinding.moduleTitle.text = "Locked - End of Lesson Challenge"
                challengeCardBinding.moduleTitle.alpha = 0.5f
                challengeCardBinding.moduleTitle.alpha = 0.5f
            }

            binding.modulesLinearLayout.addView(challengeCardBinding.card)
        }
    }
}
