package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent

class VirtualKeyboardModule: Module() {
    override val title: String = "Typing with Virtual Keyboard"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, VirtualKeyboardActivity::class.java))
    }
}