package com.github.talkbacktutorial.activities.modules.gotohomescreen

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel

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
            // update db
            updateModule()

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
        val intro = getString(R.string.go_to_home_screen_intro).trimIndent()
        // This is a very basic implementation that just asks the user to return
        // to the application by themselves
        this.ttsEngine.speakOnInitialisation(intro)
    }

    /**
     * Speaks the mid way instructions to the user
     * @author Mingxuan Fu
     */
    private fun speakMid() {
        val outro = getString(R.string.go_to_home_screen_mid).trimIndent()
        this.ttsEngine.speakOnInitialisation(outro)
    }

    /**
     * Speaks the outro to the user
     * @author Mingxuan Fu
     */
    private fun speakOutro() {
        val outro = getString(R.string.go_to_home_screen_outro).trimIndent()
        this.ttsEngine.speakOnInitialisation(outro)
    }

    /**
     * Updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule(){
        val moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, this)
        }
    }
}
