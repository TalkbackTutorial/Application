package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.addspacesnlbraillekeyboard.DNLBrailleKeyboardActivity

/**
 * Teaches the user how to delete letters and words using braille keyboard
 * @author: Mohak Malhotra
 */
class DNLBrailleKeyboardModule : Module() {
    override val title: String = "Delete letters and words"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, DNLBrailleKeyboardActivity::class.java))
    }
}