package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.modules.AdjustReadingControlsActivity

/**
 * Defines and sets up the module for learning about reading controls.
 *
 * @author Matthew Crossman
 * @see AdjustReadingControlsActivity
 */
class AdjustReadingControls : Module() {
    override val title = "Adjust reading controls"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, AdjustReadingControlsActivity::class.java))
    }
}