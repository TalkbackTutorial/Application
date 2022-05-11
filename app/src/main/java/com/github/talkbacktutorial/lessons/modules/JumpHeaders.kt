package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.jumpheaders.JumpHeadersActivity

/**
 * Module on jumping between headers using the appropriate reading mode
 *
 * @author Matthew Crossman
 */
class JumpHeaders : Module() {

    override val title = "Jump between headers"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, JumpHeadersActivity::class.java))
    }
}
