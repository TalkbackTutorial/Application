package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.scroll.ScrollActivity

/**
 * A module that teaches the user how to scroll through vertical and horizontal menus.
 * @author Andre Pham
 */
class ScrollingModule : Module() {

    override val title: String = "Scrolling Vertical and Horizontal Menus"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, ScrollActivity::class.java))
    }

}