package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.voicerecorderapp.VoiceRecorderAppActivity

/**
 * This modules involves performing a set of tasks within a custom fork of Simple Voice Recorder.
 *
 * @author Team 3
 * @see <a href="https://github.com/TalkbackTutorial/voice-recorder-fork">Repository for the forked app</a>
 */
class VoiceRecorderAppModule: Module() {

    override val title = "Voice Recorder App"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, VoiceRecorderAppActivity::class.java))
    }
}