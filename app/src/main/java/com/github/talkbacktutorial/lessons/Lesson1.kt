package com.github.talkbacktutorial.lessons

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.lesson1.Lesson1Activity
import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.modules.Module

/**
 * Lesson1 is a special case because it will be what runs when the application first opens.
 * It has no modules, just a sequence of fragments for the user to get used to navigation.
 * @author Andre Pham
 */
class Lesson1 : Lesson() {

    override val title: String = "Getting Started"
    override val sequenceNumeral: Int = 1
    override val modules: ArrayList<Module> = ArrayList()
    override val challenge: Challenge? = null

    /**
     * Starts the activity responsible for lesson 0.
     * Unlike all the other activities that share LessonActivity, this has its own activity
     * (refer to class header for more information on why).
     * @author Andre Pham
     * @param context The context that starts Lesson0's Activity.
     */
    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, Lesson1Activity::class.java))
    }
}
