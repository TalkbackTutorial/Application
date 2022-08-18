package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.goback.GoBackActivity

/**
 * A module that teaches how to return to the previous screen.
 * @author Emmanuel Chu
 */
class GoBackModule : Module() {

    override val title: String = "Go Back to Previous Page"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, GoBackActivity::class.java))
    }
}
