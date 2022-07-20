package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.voicerecorderapp.VoiceRecorderAppActivity

/**
 * Module covering the use of a custom forked version of Simple Voice Recorder.
 *
 * @author Team 4
 * @see <a href="https://github.com/TalkbackTutorial/voice-recorder-fork">Repository for the forked app</a>
 */
class VoiceRecorderAppModule: Module() {

    override val title = "Voice Recorder App"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, VoiceRecorderAppActivity::class.java))
    }
}