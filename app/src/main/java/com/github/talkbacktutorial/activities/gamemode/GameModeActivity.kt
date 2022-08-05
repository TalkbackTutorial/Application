package com.github.talkbacktutorial.activities.gamemode

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
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
        onStartRound = ::onStartRound
    )
    private lateinit var correctSound: MediaPlayer
    private lateinit var incorrectSound: MediaPlayer

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_game_mode)

        this.correctSound = MediaPlayer.create(this, R.raw.correct)
        this.incorrectSound = MediaPlayer.create(this, R.raw.wrong)

        // initialise tts
        this.ttsEngine = TextToSpeechEngine(this)
            .onFinishedSpeaking(triggerOnce = true) {
                // Start the game after the intro is finished
                this.game.startGame()
            }
        this.ttsEngine.speakOnInitialisation(   // TODO: make this better
            "To play the game perform the gesture that is spoken"
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
    }

    /**
     * Trigger actions to indicate the start of a round.
     * @author Andre Pham
     */
    private fun onStartRound() {
        this.binding.staticScoreLabel.text = this.game.requiredGesture.actionDescription
        this.ttsEngine.speak(this.game.requiredGesture.actionDescription)
    }
}