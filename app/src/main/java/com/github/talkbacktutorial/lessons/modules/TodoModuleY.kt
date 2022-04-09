package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.TodoModuleYModuleActivity

/**
 * TODO
 */
class TodoModuleY : Module() { // E.g. PausingAndPlayingMediaModule

    override val title: String = "TODO MODULE Y" // E.g. "Pause and Play Media"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, TodoModuleYModuleActivity::class.java))
    }

}