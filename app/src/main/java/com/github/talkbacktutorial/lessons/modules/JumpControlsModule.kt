package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.jumpcontrols.JumpControlsActivity

/**
 * Module on jumping between controls using the appropriate reading mode
 * @author Matthew Crossman
 */
class JumpControlsModule : Module() {

    override val title = "Jump between controls"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, JumpControlsActivity::class.java))
    }
}
