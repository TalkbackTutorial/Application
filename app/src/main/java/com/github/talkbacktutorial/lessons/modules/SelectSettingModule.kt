package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.content.Intent

class SelectSettingModule : Module() {

    override val title: String = "Selecting Setting"

    override fun startActivity(context: Context) {
        //context.startActivity(Intent(context, SelectSettingModuleActivity::class.java))
    }

}