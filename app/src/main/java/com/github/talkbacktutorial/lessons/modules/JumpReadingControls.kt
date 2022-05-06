package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.adjustreadingcontrols.AdjustReadingControlsActivity
import com.github.talkbacktutorial.activities.modules.jumpreadingcontrols.JumpReadingControlsActivity

/**
 * Defines and sets up the module for learning about reading controls.
 *
 * @author Joel Yang
 * @see AdjustReadingControlsActivity
 */
class JumpReadingControls : Module() {
    override val title = "Jump Reading Controls"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, JumpReadingControlsActivity::class.java))
    }
}