package com.github.talkbacktutorial.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.DebugSettings
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgression
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.*
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer
import com.github.talkbacktutorial.lessons.modules.Module

class LessonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLessonBinding
    private lateinit var lesson: Lesson

    private lateinit var moduleProgressionViewModel: ModuleProgressionViewModel

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

    /**
     * Pulls the required lessonProgression item from the database to determine which modules should be
     * locked.
     * @author Jade Davis
     */
    private fun displayModules() {
        moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        moduleProgressionViewModel.getAllModuleProgressions.observe(this) { modules ->
            if (modules.isNotEmpty()) {
                displayModuleCards(modules)
            }
        }
    }

    /**
     * Loads all modules in the lesson, and sets each as locked or unlocked depending on the
     * user's progression.
     * @param moduleProgression A database entry which specifies the number of modules completed
     * @author Jade Davis, Antony Loose
     */
    private fun displayModuleCards(moduleProgression: List<ModuleProgression>) {
        binding.modulesLinearLayout.removeAllViews()

        val modules = mutableListOf<Module>()
        val complete = mutableListOf<Boolean>()

        for (module in lesson.modules){
            for (mp in moduleProgression){
                if (module.title == mp.moduleName){
                    modules.add(module)
                    complete.add(mp.completed)
                }
            }
        }

        var moduleCount = -1
        do {
            moduleCount++
            loadModuleCard(modules[moduleCount], locked = false)
        } while (complete[moduleCount] && moduleCount < modules.size - 1)

        for (i in moduleCount+1 until modules.size) {
            loadModuleCard(modules[i], locked = true)
        }

        if (complete[modules.size-1]) {
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

        if (!locked || DebugSettings.bypassProgressionLocks) {
            moduleCardBinding.moduleCard.setOnClickListener {
                InstanceSingleton.getInstanceSingleton().selectedModuleName = module.title
                module.startActivity(this)
            }
        } else {
            moduleCardBinding.title = "Locked - " + module.title
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

            if (!locked || DebugSettings.bypassProgressionLocks) {
                challengeCardBinding.card.setOnClickListener {
                    challenge.startActivity(this)
                }
            } else {
                challengeCardBinding.locked = true
                challengeCardBinding.moduleTitle.alpha = 0.5f
            }

            binding.modulesLinearLayout.addView(challengeCardBinding.card)
        }
    }
}
