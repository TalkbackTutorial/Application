package com.github.talkbacktutorial.activities.challenges.lesson3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.App.Companion.context
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.ModuleProgressionViewModel
import com.github.talkbacktutorial.databinding.ChallengeLayoutBinding

class Lesson3ChallengeActivity : AppCompatActivity() {

    lateinit var binding: ChallengeLayoutBinding
        private set
    private lateinit var ttsEngine: TextToSpeechEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.challenge_layout)
        this.ttsEngine = TextToSpeechEngine(this)
        this.startChallenge()
    }

    /**
     * Introduces the challenge then pushes the first fragment.
     * @author Emmanuel Chu
     */
    private fun startChallenge() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            supportFragmentManager.commit {
                replace(R.id.frame, Lesson3ChallengePart1Fragment.newInstance())
            }
        }
        this.ttsEngine.speakOnInitialisation(getString(R.string.lesson3_challenge_intro))
    }

    fun completeChallenge() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            this.ttsEngine.speak(getString(R.string.lesson3_challenge_outro))
        }, 800)    // Avoid conflict with checkbox
        updateModule()
    }

    /**
     * Updates the database when a module is completed
     * @author Antony Loose
     */
    private fun updateModule(){
        val moduleProgressionViewModel = ViewModelProvider(this).get(ModuleProgressionViewModel::class.java)
        InstanceSingleton.getInstanceSingleton().selectedModuleName?.let {
            moduleProgressionViewModel.markModuleCompleted(it, context as Context)
        }
    }

    /**
     * Clean up the TTS engine when activity is destroyed
     *
     * @author Matthew Crossman
     */
    override fun onDestroy() {
        ttsEngine.shutDown()
        super.onDestroy()
    }


}