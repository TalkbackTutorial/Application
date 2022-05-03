package com.github.talkbacktutorial.activities.modules.adjustslider

import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.SeekBar
import com.github.talkbacktutorial.TextToSpeechEngine

/**
 * This delegate allows us to execute code before talkback reads screen. This is needed because in some cases talkback will interrupt tts and therefore,
 * the user will not be read instructions.
 * @param activity the activity for tts
 * @author Antony Loose
 */
class AdjustSliderDelegate(activity: AdjustSliderModuleActivity) : View.AccessibilityDelegate() {

    // accessibility constants
    // these values are the constant values of the accessibility event types that we need to detect
    private val typeViewAccessibilityFocused = 32768
    private val typeWindowContentChanged = 2048
    // slider vars

    // initialise tts
    private var ttsEngine = TextToSpeechEngine((activity)).onFinishedSpeaking(triggerOnce = true) {}

    /**
     * This method runs when an accessibility event occurs.
     * When the slider value changes or the slider is highlighted it will check the current event,
     * if the event is adjusting the slider value it interrupts talkback and reads out the value using tts
     * this is so that talkback doesn't interrupt our tts when reading out instructions
     * @author Antony Loose
     */
    override fun onRequestSendAccessibilityEvent(host: ViewGroup?, child: View?, event: AccessibilityEvent?
    ): Boolean {

        if (child is SeekBar){
            /*
                only execute on the events we want, when a slider is selected there are multiple accessibility events
                we only want to read our instruction on specific events, when the slider value changes and when
                the slider is highlighted.
            */
            if (event?.eventType == typeWindowContentChanged || event?.eventType == typeViewAccessibilityFocused) {

                val progress = child.progress
                // use our tts to read progress
                if (progress%5 == 0) {
                    speakText("$progress%", true)
                }

                // disable talkback from reading out progress as this can interrupt our instructions
                if (event.eventType == typeViewAccessibilityFocused) {
                    val text = """
                        slider: swipe up to increase or down to decrease
                        or double tap and hold, then move finger to adjust
                    """.trimIndent()
                    speakText(text, true)
                }
                return false
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
    }

}