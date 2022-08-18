package com.github.talkbacktutorial.activities.sandboxmode

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.TextToSpeechEngine
import com.github.talkbacktutorial.accessibilitymanager.AccessibilityChangeListener
import com.github.talkbacktutorial.accessibilitymanager.AccessibilityChangeManager
import com.github.talkbacktutorial.accessibilitymanager.AccessibilityChangePage
import com.github.talkbacktutorial.activities.MainActivity
import com.github.talkbacktutorial.databinding.ActivitySandboxModeBinding
import com.github.talkbacktutorial.gestures.GestureIdentifier
import com.github.talkbacktutorial.gestures.delegates.GestureDelegate
import com.github.talkbacktutorial.gestures.delegates.SimpleGestureDelegate

class SandboxModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySandboxModeBinding
    private val gestureIdentifier = GestureIdentifier()
    private lateinit var gestureDelegate: GestureDelegate
    private lateinit var simpleGestureDelegate: SimpleGestureDelegate
    private lateinit var ttsEngine: TextToSpeechEngine

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_sandbox_mode)
        this.toggleInterface(false)

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
                        this.ttsEngine.speak(getString(R.string.sandbox_end), override = true)
                    }, 4500)    // Avoid conflicts with reading "talkback off"
                },
                talkbackOffCallback = {
                    Handler(Looper.getMainLooper()).postDelayed({
                        this.ttsEngine.onFinishedSpeaking(triggerOnce = true) {
                            this.toggleInterface(true)
                        }
                        this.ttsEngine.speak(getString(R.string.sandbox_start))
                    }, 3000)    // Avoid conflicts with reading "talkback on, talkback tutorial"
                },
                associatedPage = AccessibilityChangePage.SANDBOX
            )
        )

        // initialise tts
        this.ttsEngine = TextToSpeechEngine(this)
        this.ttsEngine.speakOnInitialisation(
            getString(R.string.sandbox_intro)
        )

        // Setup delegates to feed data to gestureIdentifier
        this.gestureDelegate = GestureDelegate(this.gestureIdentifier.gestureData)
        this.simpleGestureDelegate = SimpleGestureDelegate(
            this,
            this.gestureIdentifier.flingMotionData,
            this.gestureIdentifier.tapData,
            this.gestureIdentifier.scrollMotionData
        )

        // Add listeners to gesture and touch overlay
        this.binding.gestureOverlay.addOnGestureListener(this.gestureDelegate)
        this.binding.touchOverlay.setOnTouchListener { view, event ->
            this.simpleGestureDelegate.onTouchEventCallback(event)
            if (event.actionMasked == MotionEvent.ACTION_UP) {
                val output = this.gestureIdentifier.onGestureConclusion()
                // speak
                this.ttsEngine.speak(output.actionDescription, true)
                this.binding.gestureText.text = output.description
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

    override fun onResume() {
        AccessibilityChangeManager.setPage(AccessibilityChangePage.SANDBOX)
        super.onResume()
    }

    override fun onStop() {
        AccessibilityChangeManager.resetPage(AccessibilityChangePage.SANDBOX)
        super.onStop()
    }
}