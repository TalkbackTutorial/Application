package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.virtualkeyboard.VirtualKeyboardActivity

class VirtualKeyboardModule: Module() {

    override val title: String = "Typing with a Virtual Keyboard"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, VirtualKeyboardActivity::class.java))
    }
}