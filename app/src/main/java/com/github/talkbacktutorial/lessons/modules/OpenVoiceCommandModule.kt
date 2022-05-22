package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.openingvoicecommand.OpenVoiceCommandActivity


class OpenVoiceCommandModule : Module() {

    override val title: String = "Open Voice Command"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, OpenVoiceCommandActivity::class.java))
    }
}
