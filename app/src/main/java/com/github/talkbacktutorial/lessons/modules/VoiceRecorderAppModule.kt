package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.voicerecorderapp.VoiceRecorderAppActivity


class VoiceRecorderAppModule: Module() {
    override val title = "Voice Recorder App"
    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, VoiceRecorderAppActivity::class.java))
    }
}