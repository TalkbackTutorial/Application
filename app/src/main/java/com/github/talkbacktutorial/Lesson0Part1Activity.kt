package com.github.talkbacktutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.databinding.ActivityLesson0Part1Binding

class Lesson0Part1Activity : AppCompatActivity() {

    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val binding: ActivityLesson0Part1Binding = DataBindingUtil.setContentView(this, R.layout.activity_lesson0_part1)
        binding.continueButton.visibility = View.GONE

        this.ttsEngine = TextToSpeechEngine(this)
            .onFinishedSpeaking(triggerOnce = true) {
                binding.continueButton.visibility = View.VISIBLE
            }

        binding.continueButton.setOnClickListener {
            startActivity(Intent(this, Lesson0Part2Activity::class.java))
        }

        val intro = """
            Welcome. 
            In your first lesson, you'll learn to move forwards and backwards between menu items, as well as interact with them.
            Double tap to continue.
        """.trimIndent()
        this.ttsEngine.speakOnActivityOpen(intro)
    }

}