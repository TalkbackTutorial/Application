package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.addspacesnlbraillekeyboard.DNLBrailleKeyboardActivity

/**
 * Teaches the user how to add spaces and new lines in braille keyboard
 * @author: Mohak Malhotra
 */
class AddSpacesNLBrailleKeyboardModule : Module() {
    override val title: String = "Add Spaces and New Line"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, DNLBrailleKeyboardActivity::class.java))
    }
}