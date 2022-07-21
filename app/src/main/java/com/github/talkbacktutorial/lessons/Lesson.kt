package com.github.talkbacktutorial.lessons

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.activities.LessonActivity
import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.modules.Module
import java.util.*

/**
 * Lessons are a sequence of tasks and explanations for the user to complete in order to learn a
 * component of Talkback, or related. For example, a lesson could educate the user on the swipe left
 * and swipe right gestures, give them an opportunity to try the gestures out, then provide a task
 * to demonstrate practical applications of the gestures they have learnt.
 *
 * Lessons are made up of modules, which is essentially what the lesson teaches, broken down. For
 * example, if you had a "Basic Navigation" lesson, you would include "Next Element", "Previous
 * Element" and "Interact with Element" modules that make up the lesson.
 *
 * All lessons share the same Activity, LessonActivity (with the exception of Lesson0). This
 * Activity generates cards for every module the lesson contains.
 *
 * @author Andre Pham
 */
abstract class Lesson() {

    companion object { // Static
        val INTENT_KEY = App.resources.getString(R.string.intent_key)
    }

    abstract val title: String
    abstract val sequenceNumeral: Int
    abstract val description: String
    val id: UUID = UUID.randomUUID()
    abstract val modules: ArrayList<Module>
    abstract val challenge: Challenge?

    var isLocked: Boolean = false
        private set

    val sequenceName: String
        get() = App.resources.getString(R.string.lesson_space) + this.sequenceNumeral

    /**
     * Starts the activity responsible for this lesson.
     * @author Andre Pham
     * @param context The context that starts this lesson's Activity.
     */
    open fun startActivity(context: Context) {
        val intent = Intent(context, LessonActivity::class.java)
        intent.putExtra(INTENT_KEY, this.id.toString())
        context.startActivity(intent)
    }

    /**
     * Gets a module's numerical placement in the sequence of modules. For example, the second
     * module in a lesson would be "Module 2", so this would return 2 for that.
     * @author Andre Pham
     * @param module The module to have its numerical placement found
     * @return The numerical placement of the module provided
     */
    fun getModuleSequenceNumeral(module: Module): Int {
        return this.modules.indexOfFirst { it.id == module.id } + 1
    }
}
