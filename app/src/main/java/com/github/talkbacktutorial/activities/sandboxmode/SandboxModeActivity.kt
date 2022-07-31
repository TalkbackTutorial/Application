package com.github.talkbacktutorial.activities.sandboxmode

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.databinding.ActivitySandboxModeBinding
import com.github.talkbacktutorial.gestures.delegates.GestureDelegate
import com.github.talkbacktutorial.gestures.GestureIdentifier
import com.github.talkbacktutorial.gestures.delegates.SimpleGestureDelegate

class SandboxModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySandboxModeBinding
    private val gestureIdentifier = GestureIdentifier()
    private lateinit var gestureDelegate: GestureDelegate
    private lateinit var simpleGestureDelegate: SimpleGestureDelegate

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_sandbox_mode)

        this.gestureDelegate = GestureDelegate(this.gestureIdentifier.gestureData)
        this.simpleGestureDelegate = SimpleGestureDelegate(
            this,
            this.gestureIdentifier.flingMotionData,
            this.gestureIdentifier.tapData,
            this.gestureIdentifier.scrollMotionData
        )

        this.binding.gestureOverlay.addOnGestureListener(this.gestureDelegate)
        this.binding.touchOverlay.setOnTouchListener { view, event ->
            this.simpleGestureDelegate.onTouchEventCallback(event)
            if (event.actionMasked == MotionEvent.ACTION_UP) {
                val output = this.gestureIdentifier.onGestureConclusion()
                Log.i("andre", output.toString())
            }
            return@setOnTouchListener true
        }
    }
}