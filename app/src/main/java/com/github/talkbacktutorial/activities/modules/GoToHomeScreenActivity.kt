package com.github.talkbacktutorial.activities.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine

class GoToHomeScreenActivity : AppCompatActivity() {
    private lateinit var ttsEngine: TextToSpeechEngine
    private var wasPaused: Boolean = false
    private lateinit var repeatBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_to_home_screen_module)
        this.ttsEngine = TextToSpeechEngine(this)
        repeatBtn = findViewById(R.id.repeatBtn)
        repeatBtn.visibility = View.GONE



        if (savedInstanceState != null) {
            wasPaused = savedInstanceState.getBoolean("wasPaused")
        }
        if (!wasPaused) {
            ttsEngine.onFinishedSpeaking {
                repeatBtn.visibility = View.VISIBLE
                repeatBtn.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(view: View?) {
                        speakIntro()
                    }
                })
            }
            this.speakIntro()
        }
    }

    override fun onStart() {
        if (wasPaused) { // This currently just checks if the activity was paused
            repeatBtn.visibility = View.GONE
            ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                finish()
            }
            this.speakOutro()
        }
        super.onStart()
    }

    override fun onStop() {
        repeatBtn.visibility = View.GONE
        wasPaused = true
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putBoolean("wasPaused", wasPaused)
        }
        super.onSaveInstanceState(outState)
    }

    /**
     * Speaks the intro to the user
     * @author Mingxuan Fu
     */
    private fun speakIntro() {
        val intro = """
            Welcome.
            In this module, you'll learn how to return to the home screen from inside an application,
            to perform this task, swipe up then left. Try it now. But return to the tutorial after you've reached the home screen.
        """.trimIndent()
        // This is a very basic implementation that just asks the user to return
        // to the application by themselves
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks the outro to the user
     * @author Mingxuan Fu
     */
    private fun speakOutro() {
        val outro = "Nice, you have navigated back to this screen, you have now learnt how to use the go to home screen gesture. " +
                "Returning you to the lesson screen now.".trimIndent()
        this.ttsEngine.speakOnInitialisation(outro)
    }
}