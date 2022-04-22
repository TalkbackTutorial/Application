package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.TodoModuleXModuleActivity

/**
 * TODO
 */
class GoToHomeScreenModule : Module() { // E.g. PausingAndPlayingMediaModule

    override val title: String = "TODO MODULE X" // E.g. "Pause and Play Media"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, TodoModuleXModuleActivity::class.java))
    }

}