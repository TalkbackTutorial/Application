package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.hidebraillekeyboard.HideBrailleKeyboardActivity

/**
 * Teaches the user how to hide the braille keyboard
 * @author: Mingxuan Fu
 */
class HideBrailleKeyboardModule : Module() {

    override val title: String = "Hide Braille Keyboard"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, HideBrailleKeyboardActivity::class.java))
    }
}