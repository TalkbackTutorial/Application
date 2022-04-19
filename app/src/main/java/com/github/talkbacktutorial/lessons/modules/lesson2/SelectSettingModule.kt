package com.github.talkbacktutorial.lessons.modules.lesson2

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.ExploreMenuByTouchActivity
import com.github.talkbacktutorial.activities.modules.SelectSettingModuleActivity
import com.github.talkbacktutorial.lessons.modules.Module

class SelectSettingModule() : Module() {

    override val title: String = "Changing Setting"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, SelectSettingModuleActivity::class.java))
    }
}