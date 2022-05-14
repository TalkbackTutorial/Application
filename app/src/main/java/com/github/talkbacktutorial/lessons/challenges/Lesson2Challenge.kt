package com.github.talkbacktutorial.lessons.challenges

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.lesson1.Lesson1Activity
import com.github.talkbacktutorial.lessons.modules.Module

/**
 * A challenge that makes use of all the content taught in Lesson2 in a practical real-world example.
 * @author Andre Pham
 */
class Lesson2Challenge : Challenge() {

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, Lesson2Challenge::class.java))
    }
}
