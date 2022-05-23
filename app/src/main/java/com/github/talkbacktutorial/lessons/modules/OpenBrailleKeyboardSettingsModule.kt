package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.openbraillekeyboardsettings.AddSpacesNLBrailleKeyboardActivity

/**
 * Teaches the user how to open the braille keyboard settings
 * @author Mingxuan Fu
 */
class OpenBrailleKeyboardSettingsModule : Module() {

    override val title: String = "Open Braille Keyboard Settings"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, AddSpacesNLBrailleKeyboardActivity::class.java))
    }
}