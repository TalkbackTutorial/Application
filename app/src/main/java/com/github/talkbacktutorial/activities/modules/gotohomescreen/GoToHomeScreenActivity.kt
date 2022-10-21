package com.github.talkbacktutorial.activities.modules.gotohomescreen

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel

class GoToHomeScreenActivity : AppCompatActivity() {

    private var stoppedCount: Int = 0
    private lateinit var display: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_go_to_home_screen_module)

        display = findViewById(R.id.gths_tv)

        if (savedInstanceState != null) {
            stoppedCount = savedInstanceState.getInt(getString(R.string.stopped_count))
        }
    }

    override fun onStart() {

        when (stoppedCount) {
            0 -> { // This currently just checks for how many times the activity stopped
                this.speakIntro()
            }
            1 -> {
                this.speakMid()
            }
            2 -> {
                // update db
                updateModule()
                display.setOnClickListener {
                    finish()
                }
                this.speakOutro()
            }
        }

        super.onStart()
    }

    override fun onStop() {
        stoppedCount += 1
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(getString(R.string.stopped_count), stoppedCount)
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
        display.text = intro
    }

    /**
     * Speaks the mid way instructions to the user
     * @author Mingxuan Fu
     */
    private fun speakMid() {
        val outro = getString(R.string.go_to_home_screen_mid).trimIndent()
        display.text = outro
    }

    /**
     * Speaks the outro to the user
     * @author Mingxuan Fu
     */
    private fun speakOutro() {
        val outro = getString(R.string.go_to_home_screen_outro).trimIndent()
        display.text = outro
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
