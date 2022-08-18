package com.github.talkbacktutorial

import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * TextToSpeechEngine will be used whenever the application needs to provide context, feedback or
 * prompts to the user outside of Talkback’s default integrations. For example, notifying the user
 * that they have successfully completed a lesson, or providing feedback on what action they are
 * performing via gesture.
 * @author Andre Pham
 * @param context The context that initiates this class
 */
class TextToSpeechEngine(context: AppCompatActivity) {

    companion object { // Static
        // A history of texts spoken
        private var history = ArrayList<String>()
    }

    private val ttsEngine: TextToSpeech

    // Functions triggered by internal events
    private var onStartSpeaking: (() -> Unit)? = null
    private var onFinishedSpeaking: (() -> Unit)? = null
    // Used to optionally nullify functions after initial call
    private var triggerOnStartSpeakingOnce: Boolean = false
    private var triggerOnFinishedSpeakingOnce: Boolean = false

    val isSpeaking: Boolean
        get() = this.ttsEngine.isSpeaking

    init {
        this.ttsEngine = TextToSpeech(context) { }
        this.ttsEngine.language = Locale.getDefault()

        try {
            // Attempt to copy user's setting's TTS pitch and rate settings
            val systemPitch = Settings.Secure.getInt(context.contentResolver, Settings.Secure.TTS_DEFAULT_PITCH)
            val systemRate = Settings.Secure.getInt(context.contentResolver, Settings.Secure.TTS_DEFAULT_RATE)
            ttsEngine.setPitch(systemPitch / 100F)
            ttsEngine.setSpeechRate(systemRate / 100F)
        } catch (e: Settings.SettingNotFoundException) { /* Default values are set instead */ }

        this.ttsEngine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            // Calls when TTS starts speaking
            override fun onStart(p0: String?) {
                context.runOnUiThread {
                    // If a method has been provided to be called when TTS starts speaking, call it
                    this@TextToSpeechEngine.onStartSpeaking?.let { it() }
                    if (triggerOnStartSpeakingOnce) this@TextToSpeechEngine.onStartSpeaking = null
                }
            }
            // Calls when TTS finishes speaking
            override fun onDone(utteranceId: String?) {
                context.runOnUiThread {
                    // If a method has been provided to be called when TTS stops speaking, call it
                    this@TextToSpeechEngine.onFinishedSpeaking?.let { it() }
                    if (triggerOnFinishedSpeakingOnce) this@TextToSpeechEngine.onFinishedSpeaking = null
                }
            }

            override fun onError(p0: String?) { } // Depreciated, yet inherited
        })
    }

    /**
     * Speaks text.
     * @author Andre Pham
     * @param text The text to speak
     * @param override If true, clears the queue of text to be spoken before speaking
     */
    fun speak(text: String, override: Boolean = false) {
        if (DebugSettings.skipTTS) {
            this.ttsEngine.speak("skip", TextToSpeech.QUEUE_FLUSH, null, "tts1")
            return
        }
        this.ttsEngine.speak(text, if (override) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD, null, "tts1")
        history.add(text)
    }

    /**
     * Repeats the last 1..* number of texts spoken.
     * @author Andre Pham
     * @param amount The number of previous texts to speak, starting at the last text spoken
     */
    fun repeatLast(amount: Int = 1) {
        for (index in 1..amount) {
            if (history.size < index) continue
            this.ttsEngine.speak(history[index - 1], TextToSpeech.QUEUE_ADD, null, "tts1")
        }
    }

    /**
     * Speaks text on the initialisation of an Activity or Fragment.
     * Calling 'speak' in the creation function of an Activity or Fragment fails, hence this.
     * @author Andre Pham
     * @param text The text to speak
     */
    fun speakOnInitialisation(text: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            this.speak(text)
        }, 1000)
    }

    /**
     * Stops any speaking occurring.
     * @author Andre Pham
     */
    fun stopSpeaking() {
        this.ttsEngine.stop()
    }

    /**
     * Shuts down the TTS engine.
     * Call before an Activity or Fragment is destroyed.
     * @author Andre Pham
     */
    fun shutDown() {
        this.ttsEngine.shutdown()
    }

    /**
     * Sets a function or code block to be called any time the engine starts speaking.
     * @author Andre Pham
     * @param triggerOnce If you want the function to trigger only the next time the engine starts speaking
     * @param call The function or code block to be called
     * @return A reference to this TextToSpeechEngine
     */
    fun onStartSpeaking(triggerOnce: Boolean = false, call: () -> Unit): TextToSpeechEngine {
        this.triggerOnStartSpeakingOnce = triggerOnce
        this.onStartSpeaking = call
        return this
    }

    /**
     * Sets a function or code block to be called any time the engine stops speaking.
     * @author Andre Pham
     * @param triggerOnce If you want the function to trigger only the next time the engine stops speaking
     * @param call The function or code block to be called
     * @return A reference to this TextToSpeechEngine
     */
    fun onFinishedSpeaking(triggerOnce: Boolean = false, call: () -> Unit): TextToSpeechEngine {
        this.triggerOnFinishedSpeakingOnce = triggerOnce
        this.onFinishedSpeaking = call
        return this
    }
}
