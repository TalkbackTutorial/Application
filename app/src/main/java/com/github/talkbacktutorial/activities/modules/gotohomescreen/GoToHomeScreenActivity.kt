package com.github.talkbacktutorial.activities.modules.gotohomescreen

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine

class GoToHomeScreenActivity : AppCompatActivity() {

    private lateinit var ttsEngine: TextToSpeechEngine
    private var stoppedCount: Int = 0
    private lateinit var repeatBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_go_to_home_screen_module)
        this.ttsEngine = TextToSpeechEngine(this)
        repeatBtn = findViewById(R.id.repeatBtn)
        repeatBtn.visibility = View.GONE

        if (savedInstanceState != null) {
            stoppedCount = savedInstanceState.getInt("stoppedCount")
        }
    }

    override fun onStart() {
        repeatBtn.visibility = View.GONE
        if (stoppedCount == 0) { // This currently just checks for how many times the activity stopped
            ttsEngine.onFinishedSpeaking {
                repeatBtn.visibility = View.VISIBLE
                repeatBtn.setOnClickListener { speakIntro() }
            }
            this.speakIntro()
        } else if (stoppedCount == 1) {
            ttsEngine.onFinishedSpeaking {
                repeatBtn.visibility = View.VISIBLE
                repeatBtn.setOnClickListener { speakMid() }
            }
            this.speakMid()
        } else if (stoppedCount == 2) {
            repeatBtn.visibility = View.GONE // disable button before speaking outro
            ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                finish()
            }
            this.speakOutro()
        }
        super.onStart()
    }

    override fun onStop() {
        repeatBtn.visibility = View.GONE
        stoppedCount += 1
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("stoppedCount", stoppedCount)
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
            to perform this task, swipe up then left. Try it now. 
            But return to the tutorial after you've reached the home screen.
        """.trimIndent()
        // This is a very basic implementation that just asks the user to return
        // to the application by themselves
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks the mid way instructions to the user
     * @author Mingxuan Fu
     */
    private fun speakMid() {
        val outro = (
            "Good, you are back, this gesture is useful as it allows you to return to the " +
                "home screen at anytime no matter where you are. Now do it again, " +
                "remember the gesture is swipe up then left"
            ).trimIndent()
        this.ttsEngine.speakOnInitialisation(outro)
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
