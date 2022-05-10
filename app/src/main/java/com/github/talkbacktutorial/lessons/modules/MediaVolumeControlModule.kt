package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.mediavolumecontrol.MediaVolumeControlModuleActivity

/**
 * A module that teaches the user how to increase or decrease their media volume.
 * @author Sandy Du & Natalie Law
 */

class MediaVolumeControlModule: Module() {

    override val title: String = "Media Volume Control"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, MediaVolumeControlModuleActivity::class.java))
    }
}