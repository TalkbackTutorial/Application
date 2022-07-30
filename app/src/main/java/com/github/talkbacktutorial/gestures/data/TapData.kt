package com.github.talkbacktutorial.gestures.data

import android.os.Handler
import android.os.Looper
import android.util.Log

class TapData {

    var tapCount: Int = 0
    var pointerCount: Int = 1
    private var timerActive: Boolean = false

    fun postReset() {
        if (!this.timerActive) {
            this.timerActive = true
            Handler(Looper.getMainLooper()).postDelayed({
                this.tapCount = 0
                this.pointerCount = 1
                this.timerActive = false
                Log.i("andre", "reset")
            }, 500)
        }
    }

}