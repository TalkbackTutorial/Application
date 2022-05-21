package com.github.talkbacktutorial.lessons.modules

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.github.talkbacktutorial.database.LessonProgression
import com.github.talkbacktutorial.database.LessonProgressionViewModel
import java.util.*

/**
 * Modules are the building blocks of lessons.
 *
 * For example, if you had a "Basic Navigation" lesson, you would include "Next Element", "Previous
 * Element" and "Interact with Element" modules that make up the lesson.
 *
 * Modules belong to their own Activity. So while all Lessons have a single Activity to represent
 * them all, LessonActivity, Modules start their own Activity so they can use specific views to
 * teach their content.
 *
 * @author Andre Pham
 */
abstract class Module {

    abstract val title: String
    val id: UUID = UUID.randomUUID()

    /**
     * Starts the activity responsible for this module.
     * @param context The context that starts this module's Activity.
     */
    abstract fun startActivity(context: Context)
}
