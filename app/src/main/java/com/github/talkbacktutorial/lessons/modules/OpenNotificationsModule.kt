package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.opennotifications.OpenNotificationActivity

/**
 * Module that teaches the user how to open notification panel
 * @author Vinh Tuan Huynh
 */
class OpenNotificationsModule : Module() {

    override val title: String = "Open Notification"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, OpenNotificationActivity::class.java))
    }
}
