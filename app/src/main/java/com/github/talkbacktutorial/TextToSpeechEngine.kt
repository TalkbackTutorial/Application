package com.github.talkbacktutorial

import android.provider.Settings
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList

class TextToSpeechEngine(context: AppCompatActivity) {

    companion object { // Static
        private var history = ArrayList<String>()
    }

    private val ttsEngine: TextToSpeech by lazy {
        TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                ttsEngine.language = Locale.getDefault()
                try {
                    val systemPitch = Settings.Secure.getInt(context.contentResolver, Settings.Secure.TTS_DEFAULT_PITCH)
                    val systemRate = Settings.Secure.getInt(context.contentResolver, Settings.Secure.TTS_DEFAULT_RATE)
                    ttsEngine.setPitch(systemPitch/100F)
                    ttsEngine.setSpeechRate(systemRate/100F)
                } catch (e: Settings.SettingNotFoundException) { /* Default values are set instead */ }
            }
        }
    }

    val isSpeaking: Boolean
        get() = this.ttsEngine.isSpeaking

    fun speak(text: String) {
        this.ttsEngine.speak(text, TextToSpeech.QUEUE_ADD, null, "tts1")
        TextToSpeechEngine.history.add(text)
    }

    fun speakWithOverride(text: String) {
        this.ttsEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1")
        TextToSpeechEngine.history.add(text)
    }

    fun repeatLast(amount: Int = 1) {
        for (index in amount downTo 1) {
            if (TextToSpeechEngine.history.size < index) continue
            this.ttsEngine.speak(TextToSpeechEngine.history[index-1], TextToSpeech.QUEUE_ADD, null, "tts1")
        }
    }

    fun stopSpeaking() {
        this.ttsEngine.stop()
    }

    fun shutDown() {
        this.ttsEngine.shutdown()
    }

}