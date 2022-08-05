package com.github.talkbacktutorial.activities.gamemode

import android.annotation.SuppressLint
import android.gesture.Gesture
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.databinding.ActivityGameModeBinding
import com.github.talkbacktutorial.gestures.GestureIdentifier
import com.github.talkbacktutorial.gestures.TalkbackGesture
import com.github.talkbacktutorial.gestures.delegates.GestureDelegate
import com.github.talkbacktutorial.gestures.delegates.SimpleGestureDelegate

class GameModeAcitivity : AppCompatActivity() {

    private var score = 0
    private lateinit var currentGesture: TalkbackGesture
    private lateinit var binding: ActivityGameModeBinding
    private val gestureIdentifier = GestureIdentifier()
    private lateinit var gesturedDelegate: GestureDelegate
    private lateinit var simpleGestureDelegate: SimpleGestureDelegate
    private lateinit var ttsEngine: TextToSpeechEngine

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_game_mode)

        // initialise tts
        this.ttsEngine = TextToSpeechEngine(this)
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
                val output = this.gestureIdentifier.onGestureConclusion()
                if (checkGesture(output)){
                    // play the next round
                    // TODO: replace "correct with "ding"
                    this.ttsEngine.speak("Correct", true)
                    this.score += 1
                    playRound()
                }else{
                    // TODO: insert "brr brr" here
                    this.ttsEngine.speak("Incorrect", true)
                    this.score = 0
                }
            }
            return@setOnTouchListener true
        }

        playRound()
    }

    private fun playRound(){
        // get next gesture
        this.currentGesture = GesturePool().takeGesture()
        this.binding.staticScoreLabel.text = currentGesture.description
        // read out gesture description
        this.ttsEngine.speak(currentGesture.description)
    }

    private fun checkGesture(gesture: TalkbackGesture): Boolean{
        return currentGesture.gestureAction() == gesture.gestureAction()
    }

}