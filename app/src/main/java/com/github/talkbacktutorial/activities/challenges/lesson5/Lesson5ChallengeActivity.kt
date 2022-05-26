package com.github.talkbacktutorial.activities.challenges.lesson5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.ChallengeLayoutBinding

class Lesson5ChallengeActivity : AppCompatActivity() {

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
     * @author Jason Wu
     */
    private fun startChallenge() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            supportFragmentManager.commit {
                replace(R.id.frame, Lesson5ChallengePart1Fragment.newInstance())
            }
        }
        this.ttsEngine.speakOnInitialisation(getString(R.string.lesson5_challenge_intro))
    }
}