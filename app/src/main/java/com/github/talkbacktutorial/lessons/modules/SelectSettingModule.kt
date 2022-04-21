package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.modules.adjustingmenusliders.SelectSettingModuleActivity

class SelectSettingModule : Module() {

    override val title: String = "Changing Setting"

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, SelectSettingModuleActivity::class.java))
    }

}