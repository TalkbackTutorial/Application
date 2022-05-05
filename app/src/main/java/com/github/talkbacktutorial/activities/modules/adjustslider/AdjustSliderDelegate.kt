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
    companion object {
        private const val TYPE_VIEW_ACCESSIBILITY_FOCUSED = 32768
        private const val TYPE_WINDOW_CONTENT_CHANGED = 2048
    }

    // initialise tts
    private val ttsEngine = TextToSpeechEngine(activity)

    /**
     * This method runs when an accessibility event occurs.
     * When the slider value changes or the slider is highlighted it will check the current event,
     * if the event is adjusting the slider value it interrupts talkback and reads out the value using tts
     * this is so that talkback doesn't interrupt our tts when reading out instructions
     * @author Antony Loose
     */
    override fun onRequestSendAccessibilityEvent(
        host: ViewGroup?, child: View?, event: AccessibilityEvent?
    ): Boolean {

        if (child is SeekBar){
            /*
                only execute on the events we want, when a slider is selected there are multiple accessibility events
                we only want to read our instruction on specific events, when the slider value changes and when
                the slider is highlighted.
            */
            if (event?.eventType == TYPE_WINDOW_CONTENT_CHANGED || event?.eventType == TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                val progress = child.progress
                // use our tts to read progress
                if (progress % 5 == 0) {
                    this.ttsEngine.speak("$progress%", true)
                }
                // disable talkback from reading out progress as this can interrupt our instructions
                return event.eventType != TYPE_WINDOW_CONTENT_CHANGED
            }
        }

        return super.onRequestSendAccessibilityEvent(host, child, event)
    }

}