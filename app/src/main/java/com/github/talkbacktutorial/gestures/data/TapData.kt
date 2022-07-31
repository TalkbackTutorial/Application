package com.github.talkbacktutorial.gestures.data

import android.os.Handler
import android.os.Looper
import android.util.Log

/**
 * Stores and manipulates data relating to a tap gesture.
 * @author Andre Pham
 */
class TapData {

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
                this.tapCount = 0
                this.pointerCount = 1
                this.timerActive = false
            }, 500)
        }
    }

}