package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.adjustreadingcontrols.AdjustReadingControlsActivity

/**
 * A module for learning about reading controls.
 * @author Matthew Crossman
 */
class AdjustReadingControlsModule : Module() {

    override val title = "Adjust reading controls"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, AdjustReadingControlsActivity::class.java))
    }
}
