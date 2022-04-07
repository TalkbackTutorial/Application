package com.github.talkbacktutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.databinding.ActivityLesson0Part2Binding
import com.github.talkbacktutorial.databinding.BasicCardBinding

class Lesson0Part2Activity : AppCompatActivity() {

    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val binding: ActivityLesson0Part2Binding = DataBindingUtil.setContentView(this, R.layout.activity_lesson0_part2)

        this.ttsEngine = TextToSpeechEngine(this)
            .onFinishedSpeaking(triggerOnce = true) {
                for (menuItemNum in 1..8) {
                    val basicCardBinding: BasicCardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.basic_card, binding.cardLinearLayout, false)
                    basicCardBinding.text = "Menu Item $menuItemNum"
                    basicCardBinding.card.setOnClickListener {
                        startActivity(Intent(this, Lesson0Part3Activity::class.java))
                    }
                    binding.cardLinearLayout.addView(basicCardBinding.card)
                }
            }

        val intro = """
            Welcome. To start, you will learn to navigate back and forth between elements on the screen.
            To go to the next element, swipe right.
            To go to the previous element, swipe left.
            Elements will always speak a description of itself.
            When you're ready to move on, double tap.
        """.trimIndent()
        this.ttsEngine.speakOnActivityOpen(intro)
    }
}