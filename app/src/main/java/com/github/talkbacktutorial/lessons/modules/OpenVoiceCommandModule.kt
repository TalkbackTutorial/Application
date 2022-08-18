package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.openingvoicecommand.OpenVoiceCommandActivity

/**
 * A module that teaches the user how to open voice command and speak some basic voice commands
 * @author Jai Clapp
 */

class OpenVoiceCommandModule : Module() {

    override val title: String = "Open Voice Command"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, OpenVoiceCommandActivity::class.java))
    }
}
