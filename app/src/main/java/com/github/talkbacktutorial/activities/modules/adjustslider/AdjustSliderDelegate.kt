package com.github.talkbacktutorial.activities.modules.adjustslider

import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.SeekBar
import com.github.talkbacktutorial.TextToSpeechEngine

/**
 * This delegate allows us to execute code before talkback reads screen. This is needed because in some cases talkback will interrupt tts and therefore,
 * the user will not be read instructions.
 * This should be used to tell the user to go to min after they reach max and to tell them they've completed the task after reaching min
 * @param maxVal the value at which you want to trigger the first instruction
 * @param minVal the value at which you want to trigger the end of the fragment
 * @author Antony Loose
 */
class AdjustSliderDelegate(maxVal: Int, minVal: Int,
                           private val msg1: String, private val msg2: String, activity: AdjustSliderModuleActivity) : View.AccessibilityDelegate() {

    // accessibility constants
    // these values are the constant values of the accessibility event types that we need to detect
    private val typeViewAccessibilityFocused = 32768
    private val typeWindowContentChanged = 2048
    // slider vars
    private val maxValue = maxVal
    private val minValue = minVal
    private var hasReachedMax = false
    private var hasReachedMin = false
    // initialise tts
    private var ttsEngine = TextToSpeechEngine((activity)).onFinishedSpeaking(triggerOnce = true) {}

    /**
     * This method runs when an accessibility event occurs.
     * When the slider value changes or the slider is highlighted it will check the current slider value,
     * if the value is equal to maxValue then read out msg1, if the value is equal to minValue than
     * read msg2 and end the fragment
     * @author Antony Loose
     */
    override fun onRequestSendAccessibilityEvent(host: ViewGroup?, child: View?, event: AccessibilityEvent?
    ): Boolean {

        if (child is SeekBar){
            /*
                only execute on the events we want, when a slider is selected there are multiple accessibility events
                we only want to read our instruction on specific events, when the slider value reaches 0/100 and when
                the slider is highlighted.
            */
            if (event?.eventType == typeWindowContentChanged || event?.eventType == typeViewAccessibilityFocused) {

                val progress = child.progress
                // use our tts to read progress
                if (progress%10 == 0) {
                    speakText("$progress%", false)
                }
                if (progress == maxValue) {
                    hasReachedMax = true
                    speakText(this.msg1, true)
                } else if (progress == minValue && hasReachedMax) {
                    hasReachedMin = true
                    speakText(this.msg2, true)
                    // TODO: finish fragment
                }

                // disable talkback from reading out progress as this can interrupt our instructions
                if (event.eventType == typeWindowContentChanged){
                    return false
                }
            }
        }

        return super.onRequestSendAccessibilityEvent(host, child, event)
    }

    /**
     * Tts reads out input text
     * @author Antony Loose
     */
    private fun speakText(text: String, override: Boolean){
        this.ttsEngine.speak(text, override)
        hasReachedMax = true
    }

    /**
     * @author Antony Loose, Jade Davis
     */
    private fun finishFragment(){
        // TODO: shut off tts and end lesson (Antony)
        // TODO: go to next fragment (Jade)
        // TODO: shut of tts
    }
}