package com.github.talkbacktutorial.activities.modules.adjustslider

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.SeekBar
import com.github.talkbacktutorial.TextToSpeechEngine

/**
 * This delegate allows us to execute code before talkback reads screen. This is needed as in some cases talkback will interrupt tts and therefore,
 * the user will not be read instructions.
 * This should be used to tell the user to go to min after they reach max and to tell them they've completed the task after reaching min
 * @param maxVal the value at which you want to trigger the first instruction
 * @param minVal the value at which you want to trigger the end of the fragment
 * @author Antony Loose
 */
class AdjustSliderDelegate(maxVal: Int, minVal: Int) : View.AccessibilityDelegate() {

    private val maxValue = maxVal
    private val minValue = minVal
    private var hasReachedMax = false
    private var hasReachedMin = false

    override fun onRequestSendAccessibilityEvent(
        host: ViewGroup?,
        child: View?,
        event: AccessibilityEvent?
    ): Boolean {

        if (child is SeekBar){
            val progress = child.progress
            if (progress == maxValue && !hasReachedMax) {
                hasReachedMax = true
                Log.d("adjustSliderDelegate", "Reached max")
                // speakText()
            } else if (progress == minValue && hasReachedMax) {
                hasReachedMin = true
                Log.d("adjustSliderDelegate", "Reached max")
                // speakText()    // TODO: Replace with ending lesson
            }
        }

        return super.onRequestSendAccessibilityEvent(host, child, event)
    }

    private fun speakText(text: String){
        // TODO
    }
}