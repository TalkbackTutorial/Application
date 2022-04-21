package com.github.talkbacktutorial.lessons.modules.lesson2

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.ScrollingModuleActivity
import com.github.talkbacktutorial.activities.modules.SelectSettingModuleActivity
import com.github.talkbacktutorial.lessons.modules.Module

class ScrollingModule : Module() {

    override val title: String = "Scrolling Vertical and Horizontal Menus"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, ScrollingModuleActivity::class.java))
    }

}