package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.openrecentapps.OpenRecentAppsActivity

/**
 * A module that teaches the user to open the recent apps menu.
 * @author Jai Clapp
 */
class OpenRecentAppsModule : Module() {

    override val title: String = "Open Recent Apps"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, OpenRecentAppsActivity::class.java))
    }
}
