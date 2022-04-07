package com.github.talkbacktutorial

import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList

/*
    TextToSpeechEngine will be used whenever the application needs to provide context, feedback or
    prompts to the user outside of Talkback’s default integrations. For example, notifying the user
    that they have successfully completed a lesson, or providing feedback on what action they are
    performing via gesture.
 */
class TextToSpeechEngine(context: AppCompatActivity) {

    companion object { // Static
        private var history = ArrayList<String>()
    }

    private val ttsEngine: TextToSpeech

    private var onStartSpeaking: (() -> Unit)? = null
    private var onFinishedSpeaking: (() -> Unit)? = null
    private var triggerOnStartSpeakingOnce: Boolean = false
    private var triggerOnFinishedSpeakingOnce: Boolean = false

    val isSpeaking: Boolean
        get() = this.ttsEngine.isSpeaking

    init {
        this.ttsEngine = TextToSpeech(context) { }
        this.ttsEngine.language = Locale.getDefault()

        try {
            val systemPitch = Settings.Secure.getInt(context.contentResolver, Settings.Secure.TTS_DEFAULT_PITCH)
            val systemRate = Settings.Secure.getInt(context.contentResolver, Settings.Secure.TTS_DEFAULT_RATE)
            ttsEngine.setPitch(systemPitch/100F)
            ttsEngine.setSpeechRate(systemRate/100F)
        } catch (e: Settings.SettingNotFoundException) { /* Default values are set instead */ }

        this.ttsEngine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(p0: String?) {
                context.runOnUiThread {
                    // If a method has been provided to be called when TTS starts speaking, call it
                    this@TextToSpeechEngine.onStartSpeaking?.let { it() }
                    if (triggerOnStartSpeakingOnce) this@TextToSpeechEngine.onStartSpeaking = null
                }
            }

            override fun onDone(utteranceId: String?) {
                context.runOnUiThread {
                    // If a method has been provided to be called when TTS stops speaking, call it
                    this@TextToSpeechEngine.onFinishedSpeaking?.let { it() }
                    if (triggerOnFinishedSpeakingOnce) this@TextToSpeechEngine.onFinishedSpeaking = null
                }
            }

            override fun onError(p0: String?) { }
        })
    }

    fun speak(text: String, override: Boolean = false) {
        this.ttsEngine.speak(text, if (override) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD, null, "tts1")
        TextToSpeechEngine.history.add(text)
    }

    fun repeatLast(amount: Int = 1) {
        for (index in 1..amount) {
            if (TextToSpeechEngine.history.size < index) continue
            this.ttsEngine.speak(TextToSpeechEngine.history[index-1], TextToSpeech.QUEUE_ADD, null, "tts1")
        }
    }

    fun speakOnActivityOpen(text: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            this.speak(text)
        }, 1000)
    }

    fun stopSpeaking() {
        this.ttsEngine.stop()
    }

    fun shutDown() {
        this.ttsEngine.shutdown()
    }

    fun onStartSpeaking(triggerOnce: Boolean = false, call: () -> Unit): TextToSpeechEngine {
        this.triggerOnStartSpeakingOnce = triggerOnce
        this.onStartSpeaking = call
        return this
    }

    fun onFinishedSpeaking(triggerOnce: Boolean = false, call: () -> Unit): TextToSpeechEngine {
        this.triggerOnFinishedSpeakingOnce = triggerOnce
        this.onFinishedSpeaking = call
        return this
    }

}