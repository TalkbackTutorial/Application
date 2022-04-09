package com.github.talkbacktutorial.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityLessonBinding
import com.github.talkbacktutorial.databinding.ModuleCardBinding
import com.github.talkbacktutorial.lessons.Lesson
import com.github.talkbacktutorial.lessons.LessonContainer

class LessonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val binding: ActivityLessonBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_lesson
        )

        // Show all modules in the lesson
        this.intent.getStringExtra(Lesson.INTENT_KEY)?.let { id ->
            val lesson: Lesson = LessonContainer.getLesson(id)
            binding.lesson = lesson
            for (module in lesson.modules) {
                val moduleCardBinding: ModuleCardBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.module_card, binding.modulesLinearLayout, false)
                moduleCardBinding.title = module.title
                moduleCardBinding.subtitle = "Module ${lesson.getModuleSequenceNumeral(module)}"
                moduleCardBinding.moduleCard.setOnClickListener {
                    module.startActivity(this)
                }
                binding.modulesLinearLayout.addView(moduleCardBinding.moduleCard)
            }
        }
    }

}