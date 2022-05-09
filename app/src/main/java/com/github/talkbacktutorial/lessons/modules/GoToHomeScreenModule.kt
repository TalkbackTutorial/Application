package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.GoToHomeScreenActivity

/**
 * Teach the user how to use gestures to return to the home screen
 * @author: Mingxuan Fu
 */
class GoToHomeScreenModule : Module() {

    override val title: String = "Go To Home Screen"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, GoToHomeScreenActivity::class.java))
    }

}