package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.adjustslider.AdjustSliderModuleActivity

/**
 * A module that teaches the user how to adjust sliders up and down.
 * @author Antony Loose, Jade Davis
 */
class AdjustSliderModule : Module() {

    override val title = "Adjust Slider"

    override fun startActivity(context: Context) {
         context.startActivity(Intent(context, AdjustSliderModuleActivity::class.java))
    }

}