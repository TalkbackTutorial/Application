package com.github.talkbacktutorial.activities.lesson1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivityLesson1Binding

class Lesson1Activity : AppCompatActivity() {

    lateinit var binding: ActivityLesson1Binding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_lesson1)
        supportFragmentManager.commit {
            replace(binding.frame.id, Lesson1Part1Fragment.newInstance())
        }
    }
}
