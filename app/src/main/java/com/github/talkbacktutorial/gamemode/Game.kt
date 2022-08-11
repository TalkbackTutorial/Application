package com.github.talkbacktutorial.gamemode

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.github.talkbacktutorial.gestures.TalkbackAction
import com.github.talkbacktutorial.gestures.TalkbackGesture
import com.github.talkbacktutorial.gestures.data.TapData

/**
 * Represents a game where the user is tasked to perform the gesture that corresponds to a given
 * action, and gains points if they perform streaks of correct answers.
 * @author Andre Pham
 */
class Game(
    private var onCorrectGesture: (() -> Unit),
    private var onWrongGesture: (() -> Unit),
    private var onStartRound: (() -> Unit),
    private var readScore: ((score: Int) -> Unit)
) {

    var score = 0
        private set
    var previousScore = 0
        private set
    var requiredGesture: TalkbackGesture = TalkbackGesture.NO_MATCH
        private set
    private var performedGesture: TalkbackGesture = TalkbackGesture.NO_MATCH
    private val gesturePool = GesturePool()
    private var timerActive: Boolean = false
    private var inputDisabled = true

    /**
     * Handle a gesture being performed.
     * @param gesture The gesture performed
     * @author Andre Pham
     */
    fun gesturePerformed(gesture: TalkbackGesture) {
        if (inputDisabled) { return }
        this.performedGesture = gesture
        // A timer is set up because multi-tap gestures trigger multiple gestures performed.
        // For example, a double tap will first trigger a single tap gesture, then only on the
        // second tap does the double tap gesture trigger.
        // When any gesture is begun, this function triggers. We provide a minor delay for the
        // user to complete any tap gesture, before reading the final outcome of the gesture.
        if (gesture.isTapGesture() || gesture.action == TalkbackAction.NONE) {
            if (!this.timerActive) {
                this.timerActive = true
                Handler(Looper.getMainLooper()).postDelayed({
                    this.reactToGesture()
                    this.timerActive = false
                }, TapData.TAP_GESTURE_DURATION)
            }
        } else {
            this.reactToGesture()
        }
    }

    /**
     * Starts the game.
     * @author Andre Pham
     */
    fun startGame() {
        this.inputDisabled = false
        this.requiredGesture = this.gesturePool.takeGesture()
        this.onStartRound()
    }

    /**
     * Disable input.
     * @author Andre Pham
     */
    fun disableInput() {
        this.inputDisabled = true
    }

    /**
     * React to the currently set performedGesture being performed.
     * @author Andre Pham, Antony Loose
     */
    private fun reactToGesture() {
        // if the user does a single tap read out the score
        if (this.performedGesture == TalkbackGesture.TAP){
            this.readScore(this.score)
        } else if (this.gestureMatches()) {
            this.score += 1
            this.onCorrectGesture()
            this.requiredGesture = this.gesturePool.takeGesture()
            this.onStartRound()
        } else {
            this.previousScore = this.score
            this.score = 0
            this.onWrongGesture()
        }
    }

    /**
     * Check if the performed gesture matches the required gesture.
     * @return True if the performed gesture matches the required gesture.
     * @author Andre Pham
     */
    private fun gestureMatches(): Boolean {
        return this.performedGesture.gestureActionMatches(this.requiredGesture)
    }

}