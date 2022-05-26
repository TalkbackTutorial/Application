package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.opentalkbackmenu.OpenTalkbackMenuActivity

/**
 * Module that teaches the user how to open talkback menu
 * @author Vinh Tuan Huynh
 */
class OpenTalkbackMenuModule : Module() {

    override val title: String = "Open Talkback Menu"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, OpenTalkbackMenuActivity::class.java))
    }
}
