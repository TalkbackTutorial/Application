package com.github.talkbacktutorial.gamemode

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.github.talkbacktutorial.gestures.TalkbackGesture
import com.github.talkbacktutorial.gestures.data.TapData

class Game(
    private var onCorrectGesture: (() -> Unit),
    private var onWrongGesture: (() -> Unit),
    private var onStartRound: (() -> Unit)
) {

    var score = 0
        private set
    var requiredGesture: TalkbackGesture = TalkbackGesture.NO_MATCH
        private set
    private var performedGesture: TalkbackGesture = TalkbackGesture.NO_MATCH
    private val gesturePool = GesturePool()
    private var timerActive: Boolean = false

    /**
     * Handle a gesture being performed.
     * @param gesture The gesture performed
     * @author Andre Pham
     */
    fun gesturePerformed(gesture: TalkbackGesture) {
        this.performedGesture = gesture
        // A timer is set up because multi-tap gestures trigger multiple gestures performed.
        // For example, a double tap will first trigger a single tap gesture, then only on the
        // second tap does the double tap gesture trigger.
        // When any gesture is begun, this function triggers. We provide a minor delay for the
        // user to complete the gesture, before reading the final outcome of the gesture.
        if (!this.timerActive) {
            this.timerActive = true
            Handler(Looper.getMainLooper()).postDelayed({
                this.reactToGesture()
                this.timerActive = false
            }, TapData.TAP_GESTURE_DURATION)
        }
    }

    /**
     * Starts the game.
     * @author Andre Pham
     */
    fun startGame() {
        this.requiredGesture = this.gesturePool.takeGesture()
        this.onStartRound()
    }

    /**
     * React to the currently set performedGesture being performed.
     * @author Andre Pham
     */
    private fun reactToGesture() {
        if (this.gestureMatches()) {
            this.score += 1
            this.onCorrectGesture()
            this.requiredGesture = this.gesturePool.takeGesture()
            this.onStartRound()
        } else {
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