package com.github.talkbacktutorial.activities.challenges.lesson2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.ChallengeLayoutBinding
import com.github.talkbacktutorial.lessons.challenges.lesson2challenge.Toy
import com.github.talkbacktutorial.lessons.challenges.lesson2challenge.ToyContainer
import com.github.talkbacktutorial.lessons.challenges.lesson2challenge.ToyWatchlist

class Lesson2ChallengeActivity : AppCompatActivity() {

    lateinit var binding: ChallengeLayoutBinding
        private set
    private lateinit var ttsEngine: TextToSpeechEngine
    val watchlist = ToyWatchlist()
    private val targetToys = ArrayList<Toy>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.challenge_layout)
        this.ttsEngine = TextToSpeechEngine(this)
        this.targetToys.addAll(listOf(
            ToyContainer.getToy(getString(R.string.tennis_ball)),
            ToyContainer.getToy(getString(R.string.tennis_racquet))
        ))
        this.startChallenge()
    }

    /**
     * Introduces the challenge then pushes the first fragment.
     * @author Andre Pham
     */
    private fun startChallenge() {
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            supportFragmentManager.commit {
                replace(R.id.frame, ToyShopFragment())
            }
        }
        this.ttsEngine.speakOnInitialisation(getString(R.string.lesson2_challenge_intro))
    }

    /**
     * Checks if the challenge requirements have been met. If so, the lesson is ended with closure.
     * @author Andre Pham
     */
    fun checkChallengeComplete() {
        if (this.targetToys.filter { this.watchlist.containsToy(it) }.size == this.targetToys.size) {
            this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            this.ttsEngine.speak(getString(R.string.challenge_outro))
        }
    }

    /**
     * TTS needs to be cleaned up with the activity or else it stays alive. If it's speaking when
     * pressing back, it will crash the app when it's done.
     *
     * @author Matthew Crossman
     */
    override fun onDestroy() {
        ttsEngine.shutDown()
        super.onDestroy()
    }
}