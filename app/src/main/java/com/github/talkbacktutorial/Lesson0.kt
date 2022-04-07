package com.github.talkbacktutorial

import android.content.Context
import android.content.Intent

/*
    Lesson0 is a special case because it will be what runs when the application first opens.
    It has no modules, just a sequence of activities for the user to get used to navigation.
 */
class Lesson0 : Lesson() {

    override val title: String = "Getting Started"
    override val sequenceNumeral: Int = 0
    override val modules: ArrayList<Module> = ArrayList()

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, Lesson0Part1Activity::class.java))
    }

}