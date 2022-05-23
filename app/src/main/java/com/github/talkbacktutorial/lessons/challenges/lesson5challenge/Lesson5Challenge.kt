package com.github.talkbacktutorial.lessons.challenges.lesson5challenge

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.challenges.lesson2.Lesson2ChallengeActivity
import com.github.talkbacktutorial.activities.challenges.lesson5.Lesson5ChallengeActivity
import com.github.talkbacktutorial.lessons.challenges.Challenge

/**
 * A challenge that makes use of all the content taught in Lesson5 in a practical real-world example.
 * @author Jason Wu
 */
class Lesson5Challenge : Challenge() {

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, Lesson5ChallengeActivity::class.java))
    }
}