package com.github.talkbacktutorial.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityLessonBinding
import com.github.talkbacktutorial.databinding.BasicCardBinding
import com.github.talkbacktutorial.databinding.ChallengeCardBinding
import com.github.talkbacktutorial.databinding.ModuleCardBinding
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer

class LessonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLessonBinding
    private lateinit var lesson: Lesson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_lesson)

        this.intent.getStringExtra(Lesson.INTENT_KEY)?.let { id ->
            this.lesson = LessonContainer.getLesson(id)
            binding.lesson = this.lesson
            this.setupModules()
            this.setupChallenge()
        }
    }

    /**
     * Adds a module card for each module the lesson holds, which starts the respective
     * module when clicked.
     * @author Andre Pham
     */
    private fun setupModules() {
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

    /**
     * Adds a challenge card if the lesson has a challenge. Starts the challenge when clicked.
     * @author Andre Pham
     */
    private fun setupChallenge() {
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
            challengeCardBinding.card.setOnClickListener {
                challenge.startActivity(this)
            }
            binding.modulesLinearLayout.addView(challengeCardBinding.card)
        }
    }
}
