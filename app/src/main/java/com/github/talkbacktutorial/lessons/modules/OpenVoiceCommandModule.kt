package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.openingvoicecommand.OpenVoiceCommandActivity

/**
 * TODO
 */
class OpenVoiceCommandModule : Module() { // E.g. PausingAndPlayingMediaModule

    override val title: String = "Open Voice Command" // E.g. "Pause and Play Media"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, OpenVoiceCommandActivity::class.java))
    }
}
