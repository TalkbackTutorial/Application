package com.github.talkbacktutorial.activities.gamemode

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.github.talkbacktutorial.DebugSettings
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.accessibilitymanager.AccessibilityChangeListener
import com.github.talkbacktutorial.accessibilitymanager.AccessibilityChangeManager
import com.github.talkbacktutorial.accessibilitymanager.AccessibilityChangePage
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.database.InstanceSingleton
import com.github.talkbacktutorial.database.gamemode.GameModeViewModel
import com.github.talkbacktutorial.databinding.ActivityGameModeBinding
import com.github.talkbacktutorial.gamemode.Game
import com.github.talkbacktutorial.gestures.GestureIdentifier
import com.github.talkbacktutorial.gestures.delegates.GestureDelegate
import com.github.talkbacktutorial.gestures.delegates.SimpleGestureDelegate

class GameModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameModeBinding
    private val gestureIdentifier = GestureIdentifier()
    private lateinit var gesturedDelegate: GestureDelegate
    private lateinit var simpleGestureDelegate: SimpleGestureDelegate
    private lateinit var ttsEngine: TextToSpeechEngine
    private val game = Game(
        onCorrectGesture = ::onCorrectGesture,
        onWrongGesture = ::onWrongGesture,
        onStartRound = ::onStartRound,
        readScore = ::readScore,
        onNewHighScore = ::newHighScore
    )
    private lateinit var correctSound: MediaPlayer
    private lateinit var incorrectSound: MediaPlayer

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_game_mode)
        this.toggleInterface(false)

        this.correctSound = MediaPlayer.create(this, R.raw.correct)
        this.incorrectSound = MediaPlayer.create(this, R.raw.wrong)

        AccessibilityChangeManager.setListener(
            AccessibilityChangeListener(
                talkbackOnCallback = {
                    this.toggleInterface(false)
                    this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        this.ttsEngine.speak(getString(R.string.game_end, this.game.score), override = true)
                    }, 4500)    // Avoid conflicts with reading "talkback off"
                },
                talkbackOffCallback = {
                    Handler(Looper.getMainLooper()).postDelayed({
                        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                            this.toggleInterface(true)
                            this.game.startGame()
                        }
                        this.ttsEngine.speak(getString(R.string.game_start))
                    }, 3000)    // Avoid conflicts with reading "talkback on, talkback tutorial"
                },
                associatedPage = AccessibilityChangePage.GAME
            )
        )

        // initialise tts
        this.ttsEngine = TextToSpeechEngine(this)
        this.ttsEngine.speakOnInitialisation(
            getString(R.string.game_intro)
        )

        // Setup gesture delegates
        this.gesturedDelegate = GestureDelegate(this.gestureIdentifier.gestureData)
        this.simpleGestureDelegate = SimpleGestureDelegate(
            this,
            this.gestureIdentifier.flingMotionData,
            this.gestureIdentifier.tapData,
            this.gestureIdentifier.scrollMotionData
        )

        // add listeners to overlay
        this.binding.gestureOverlay.addOnGestureListener(this.gesturedDelegate)
        this.binding.touchOverlay.setOnTouchListener { view, event ->
            this.simpleGestureDelegate.onTouchEventCallback(event)
            if (event.actionMasked == MotionEvent.ACTION_UP) {
                this.game.gesturePerformed(this.gestureIdentifier.onGestureConclusion())
            }
            return@setOnTouchListener true
        }
    }

    /**
     * Toggle whether or not the interface is shown.
     * @author Andre Pham
     */
    private fun toggleInterface(show: Boolean) {
        this.binding.touchOverlay.visibility = if (show) View.VISIBLE else View.GONE
    }

    /**
     * React to the correct gesture performed.
     * @author Andre Pham
     */
    private fun onCorrectGesture() {
        this.correctSound.start()
        this.binding.scoreLabel.text = this.game.score.toString()
    }

    /**
     * React to the wrong gesture performed.
     * @author Andre Pham
     */
    private fun onWrongGesture() {
        this.incorrectSound.start()
        this.binding.scoreLabel.text = this.game.score.toString()
        this.game.disableInput()
        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
            this.game.startGame()
        }
        this.ttsEngine.speak(getString(R.string.game_wrong_gesture, this.game.previousScore, this.game.requiredGesture.description), override = true)
    }

    /**
     * Read the current score.
     * @author Antony Loose
     */
    private fun readScore(score: Int){
        var alreadyRead = false
        getHighScore().observe(this) { highScore ->
            if (!alreadyRead){
                this.ttsEngine.speak(getString(R.string.game_read_score) + score + getString(R.string.game_read_high_score) + highScore, false)
                alreadyRead = true
            }
        }
    }

    /**
     * Trigger actions to indicate the start of a round.
     * @author Andre Pham
     */
    private fun onStartRound() {
        this.binding.staticScoreLabel.text = this.game.requiredGesture.actionDescription
        this.ttsEngine.speak(this.game.requiredGesture.actionDescription)
    }

    /**
     * Get the current high score
     * @author Antony Loose
     */
    private fun getHighScore(): LiveData<Int>{
        val gameModeDbController = ViewModelProvider(this).get(GameModeViewModel::class.java)
        return gameModeDbController.getHighScore()
    }

    /**
     * A function for checking if a new high score is set and if so updating the high score and informing the player
     * @author Antony Loose
     */
    private fun newHighScore(score: Int){
        val gameModeDbController = ViewModelProvider(this).get(GameModeViewModel::class.java)
        gameModeDbController.getHighScore().observe(this) { highScore ->
            if (highScore < score){
                gameModeDbController.addHighScore(score)
                this.ttsEngine.speak(getString(R.string.game_new_high_score) + score)
            }
        }
    }

    override fun onResume() {
        AccessibilityChangeManager.setPage(AccessibilityChangePage.GAME)
        super.onResume()
    }

    override fun onStop() {
        AccessibilityChangeManager.resetPage(AccessibilityChangePage.GAME)
        super.onStop()
    }
}