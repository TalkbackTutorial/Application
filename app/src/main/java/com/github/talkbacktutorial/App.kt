package com.github.talkbacktutorial

import android.app.Application
import android.content.res.Resources

/**
 * A class that manages the application context.
 * Use this to get the current context of the application, or to access resources
 * outside of an activity/fragment.
 * @author Andre Pham
 */
class App : Application() {

    companion object {
        lateinit var context: App
            private set

        // E.g. App.resources.getString(R.string.my_string)
        val resources: Resources
            get() = context.resources
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}