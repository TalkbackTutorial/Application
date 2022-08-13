package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.virtualkeyboard.VirtualKeyboardActivity

/**
 * A module that teaches the user how to open and use the virtual/on-screen keyboard
 * @author Team4
 */
class VirtualKeyboardModule: Module() {

    override val title: String = "Typing with a Virtual Keyboard"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, VirtualKeyboardActivity::class.java))
    }
}