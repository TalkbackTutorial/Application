package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.ExploreMenuByTouchActivity

/**
 * Explore menu by touch module
 * @author Jason Wu
 */
class ExploreMenuByTouchModule : Module() {
    override val title: String = "Explore Menu By Touch" // E.g. "Pause and Play Media"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, ExploreMenuByTouchActivity::class.java))
    }
}