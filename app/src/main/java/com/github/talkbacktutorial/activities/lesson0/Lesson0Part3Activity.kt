package com.github.talkbacktutorial.activities.lesson0

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.ActivityLesson0Part3Binding
import com.github.talkbacktutorial.databinding.BasicCardBinding
import com.github.talkbacktutorial.databinding.PillButtonBinding
import com.github.talkbacktutorial.databinding.WidePillButtonBinding

class Lesson0Part3Activity : AppCompatActivity() {

    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val binding: ActivityLesson0Part3Binding = DataBindingUtil.setContentView(this,
            R.layout.activity_lesson0_part3
        )

        this.ttsEngine = TextToSpeechEngine(this)
            .onFinishedSpeaking(triggerOnce = true) {
                for (menuItemNum in 1..6) {
                    val basicCardBinding: BasicCardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.basic_card, binding.linearLayout, false)
                    basicCardBinding.text = "Menu Item $menuItemNum"
                    basicCardBinding.card.setOnClickListener {
                        ttsEngine.speak("You have interacted with a menu item. Find the button, then double tap to finish the lesson.")
                    }
                    binding.linearLayout.addView(basicCardBinding.card)
                    if (menuItemNum == 1) basicCardBinding.card.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                }
                val primaryButtonBinding: WidePillButtonBinding = DataBindingUtil.inflate(layoutInflater, R.layout.wide_pill_button, binding.linearLayout, false)
                primaryButtonBinding.text = "Finish Lesson"
                primaryButtonBinding.button.setOnClickListener {
                    this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }
                    this.ttsEngine.speak("You have completed the lesson. Sending you to the main menu.", override = true)
                }
                binding.linearLayout.addView(primaryButtonBinding.button, 4)
            }

        val intro = """
            Welcome. You will now learn to interact with elements on the screen.
            To interact with an element, double tap when you have selected it.
            For this activity, explore the elements on the screen until you reach the button.
            To tap the button and finish the lesson, double tap.
        """.trimIndent()
        this.ttsEngine.speakOnActivityOpen(intro)
    }
}