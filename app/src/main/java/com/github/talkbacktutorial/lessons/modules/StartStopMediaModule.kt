package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.startstopmedia.StartStopMediaActivity

/**
 * A module that teaches the user how to start and stop media.
 * @author Sandy Du & Natalie Law
 */
class StartStopMediaModule : Module() {

    override val title: String = "Start and Stop Media"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, StartStopMediaActivity::class.java))
    }
}
