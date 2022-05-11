package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.explorebytouch.ExploreMenuByTouchActivity

/**
 * Explore menu by touch module
 * @author Jason Wu
 */
class ExploreMenuByTouchModule : Module() {

    override val title: String = "Explore Menu by Touch"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, ExploreMenuByTouchActivity::class.java))
    }
}
