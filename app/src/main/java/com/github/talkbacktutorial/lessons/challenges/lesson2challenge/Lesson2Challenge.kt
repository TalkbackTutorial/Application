package com.github.talkbacktutorial.lessons.challenges.lesson2challenge

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.challenges.lesson2.Lesson2ChallengeActivity
import com.github.talkbacktutorial.lessons.challenges.Challenge

/**
 * A challenge that makes use of all the content taught in Lesson2 in a practical real-world example.
 * @author Andre Pham
 */
class Lesson2Challenge : Challenge() {

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, Lesson2ChallengeActivity::class.java))
    }
}
