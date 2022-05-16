package com.github.talkbacktutorial

import android.app.Application
import android.content.Context
import android.content.res.Resources

class App : Application() {

    companion object {
        lateinit var context: App
            private set

        val resources: Resources
            get() = context.resources
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}