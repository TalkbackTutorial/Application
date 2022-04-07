package com.github.talkbacktutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.databinding.ActivityMainBinding
import com.github.talkbacktutorial.databinding.LessonCardBinding

class MainActivity : AppCompatActivity() {

    private lateinit var ttsEngine: TextToSpeechEngine

    private val lessonsModel: LessonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lessonsModel = this.lessonsModel
        this.ttsEngine = TextToSpeechEngine(this)

        for (lesson in LessonContainer.getAllLessons()) {
            val lessonCardBinding: LessonCardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.lesson_card, binding.lessonLinearLayout, false)
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