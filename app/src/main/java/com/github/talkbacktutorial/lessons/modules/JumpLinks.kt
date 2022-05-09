package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.jumplinks.JumpLinksActivity

/**
 * Module on jumping between links using the appropriate reading mode
 *
 * @author Matthew Crossman
 */
class JumpLinks : Module() {
    override val title = "Jump between links"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, JumpLinksActivity::class.java))
    }
}