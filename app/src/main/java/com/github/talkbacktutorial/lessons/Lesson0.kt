package com.github.talkbacktutorial.lessons

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.lesson0.Lesson0Part1Activity
import com.github.talkbacktutorial.lessons.modules.Module

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