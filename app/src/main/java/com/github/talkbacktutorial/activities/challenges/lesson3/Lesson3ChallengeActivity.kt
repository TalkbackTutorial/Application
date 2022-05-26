package com.github.talkbacktutorial.activities.challenges.lesson3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.activities.challenges.lesson2.ToyShopFragment
import com.github.talkbacktutorial.activities.challenges.lesson4.ArticleFragment
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


}