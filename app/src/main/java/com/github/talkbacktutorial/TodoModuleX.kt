package com.github.talkbacktutorial

import android.content.Context
import android.content.Intent

class TodoModuleX : Module() { // E.g. PausingAndPlayingMediaModule

    override val title: String = "TODO MODULE X" // E.g. "Pause and Play Media"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, TodoModuleXModuleActivity::class.java))
    }

}