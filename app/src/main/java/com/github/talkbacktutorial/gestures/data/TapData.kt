package com.github.talkbacktutorial.gestures.data

import android.os.Handler
import android.os.Looper

/**
 * Stores and manipulates data relating to a tap gesture.
 * @author Andre Pham
 */
class TapData {

    companion object {
        const val TAP_GESTURE_DURATION: Long = 500L
    }

    var tapCount: Int = 0
    var pointerCount: Int = 1
    private var timerActive: Boolean = false

    /**
     * Reset this class's tap data.
     * A delay is incurred to avoid resetting before a double/triple tap can be completed.
     * @author Andre Pham
     */
    fun postReset() {
        if (!this.timerActive) {
            this.timerActive = true
            Handler(Looper.getMainLooper()).postDelayed({
                this.reset()
                this.timerActive = false
            }, TAP_GESTURE_DURATION)
        }
    }

    /**
     * Immediately reset this class's tap data.
     * @author Andre Pham
     */
    fun reset() {
        this.tapCount = 0
        this.pointerCount = 1
    }

}