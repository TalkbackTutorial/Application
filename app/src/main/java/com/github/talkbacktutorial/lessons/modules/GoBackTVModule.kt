package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.gobacktextview.GoBackTVActivity

/**
 * Tests TextView for GoBackModule.
 *
 * @author Matthew Crossman
 */
class GoBackTVModule : Module() {

    override val title: String = "(TextView) Go Back to Previous Page"
    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, GoBackTVActivity::class.java))
    }

}