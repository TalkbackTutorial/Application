package com.github.talkbacktutorial.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.viewmodels.LessonsViewModel
import com.github.talkbacktutorial.databinding.ActivityMainBinding
import com.github.talkbacktutorial.databinding.LessonCardBinding
import com.github.talkbacktutorial.lessons.LessonContainer

class MainActivity : AppCompatActivity() {

    private lateinit var ttsEngine: TextToSpeechEngine
    private val lessonsModel: LessonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lessonsModel = this.lessonsModel
        this.ttsEngine = TextToSpeechEngine(this)

        // Show all lessons in LessonContainer
        for (lesson in LessonContainer.getAllLessons()) {
            val lessonCardBinding: LessonCardBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.lesson_card, binding.lessonLinearLayout, false
            )
            lessonCardBinding.title = lesson.title
            lessonCardBinding.subtitle = lesson.sequenceName
            lessonCardBinding.locked = lesson.isLocked
            lessonCardBinding.lessonCard.setOnClickListener {
                lesson.startActivity(this)
            }
            binding.lessonLinearLayout.addView(lessonCardBinding.lessonCard)
        }
    }
}
