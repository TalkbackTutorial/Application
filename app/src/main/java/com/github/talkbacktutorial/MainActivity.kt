package com.github.talkbacktutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import com.github.talkbacktutorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val ttsEngine = TextToSpeechEngine(this)
    private lateinit var lesson1Card: CardView
    private lateinit var lesson2Card: CardView
    private lateinit var lesson3Card: CardView
    private lateinit var lesson4Card: CardView

    private val lessonsModel: LessonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.elevation = 0F // Would be better in XML
        supportActionBar?.title = ""

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lessonsModel = this.lessonsModel

        this.lesson1Card = binding.lesson1Card.lessonCard
        this.lesson2Card = binding.lesson2Card.lessonCard
        this.lesson3Card = binding.lesson3Card.lessonCard
        this.lesson4Card = binding.lesson4Card.lessonCard

        lessonsModel.lesson1Locked.observe(this) { value ->
            if (binding.lesson1Card.locked == value) return@observe
            binding.lesson1Card.locked = value
            this.ttsEngine.speak("Lesson 1 ${if (value) "locked" else "unlocked"}")
        }
        lessonsModel.lesson2Locked.observe(this) { value ->
            if (binding.lesson2Card.locked == value) return@observe
            binding.lesson2Card.locked = value
            this.ttsEngine.speak("Lesson 2 ${if (value) "locked" else "unlocked"}")
        }
        lessonsModel.lesson3Locked.observe(this) { value ->
            if (binding.lesson3Card.locked == value) return@observe
            binding.lesson3Card.locked = value
            this.ttsEngine.speak("Lesson 3 ${if (value) "locked" else "unlocked"}")
        }
        lessonsModel.lesson4Locked.observe(this) { value ->
            if (binding.lesson4Card.locked == value) return@observe
            binding.lesson4Card.locked = value
            this.ttsEngine.speak("Lesson 4 ${if (value) "locked" else "unlocked"}")
        }

        this.lesson1Card.setOnClickListener {
            this.lessonsModel.lesson2Locked.value = false
        }
        this.lesson2Card.setOnClickListener {
            this.lessonsModel.lesson3Locked.value = false
        }
        this.lesson3Card.setOnClickListener {
            this.lessonsModel.lesson4Locked.value = false
        }
        this.lesson4Card.setOnClickListener {
            Toast.makeText(this, "You did it :p", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        this.ttsEngine.stopSpeaking()
        super.onPause()
    }

    override fun onDestroy() {
        this.ttsEngine.shutDown()
        super.onDestroy()
    }

}