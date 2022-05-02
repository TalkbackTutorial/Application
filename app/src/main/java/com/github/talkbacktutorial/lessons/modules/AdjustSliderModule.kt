package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.adjustslider.AdjustSliderModuleActivity

class AdjustSliderModule : Module() {

    override val title = "Adjust Slider"

    override fun startActivity(context: Context) {
         context.startActivity(Intent(context, AdjustSliderModuleActivity::class.java))
    }
}