package com.github.talkbacktutorial.lessons.challenges

import android.content.Context
import java.util.*

/**
 * Challenges are essentially a module that's at the end of a lesson, and makes use of all the
 * content previously presented in that lesson in a practical real-world example.
 * They are the bridge between learning a gesture for the first time and jumping to other apps
 * to use said gesture.
 * @author Andre Pham
 */
abstract class Challenge {

    val id: UUID = UUID.randomUUID()

    /**
     * Starts the activity responsible for this challenge.
     * @param context The context that starts this challenge's Activity.
     */
    abstract fun startActivity(context: Context)
}