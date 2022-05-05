package com.github.talkbacktutorial.activities.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine

class GoToHomeScreenModule : AppCompatActivity() {
    private lateinit var ttsEngine: TextToSpeechEngine
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_to_home_screen_module)
        this.ttsEngine = TextToSpeechEngine(this)
            .onFinishedSpeaking(triggerOnce = true) {
            }
        this.speakIntro()
    }
    private fun speakIntro() {
        val intro = """
            Welcome.
            In this module, you'll learn how to return to the home screen from inside an application,
            to perform this task, swipe up then left. Try it now.
        """.trimIndent()
        this.ttsEngine.speakOnInitialisation(intro)
    }
}