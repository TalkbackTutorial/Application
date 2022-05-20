package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.braillekeyboard.BrailleKeyboardActivity

class BrailleKeyboardModule: Module() {
    override val title: String = "Open Braille Keyboard"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, BrailleKeyboardActivity::class.java))
    }
}
