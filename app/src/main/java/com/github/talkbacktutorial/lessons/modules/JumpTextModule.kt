package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.adjustreadingcontrols.AdjustReadingControlsActivity
import com.github.talkbacktutorial.activities.modules.jumptext.JumpTextActivity

/**
 * A module for learning about reading controls.
 * @author Joel Yang
 */
class JumpTextModule : Module() {

    override val title = "Jump through text"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, JumpTextActivity::class.java))
    }
}
