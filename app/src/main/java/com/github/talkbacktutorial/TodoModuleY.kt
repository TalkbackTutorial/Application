package com.github.talkbacktutorial

import android.content.Context
import android.content.Intent

class TodoModuleY : Module() { // E.g. PausingAndPlayingMediaModule

    override val title: String = "TODO MODULE Y" // E.g. "Pause and Play Media"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, TodoModuleYModuleActivity::class.java))
    }

}