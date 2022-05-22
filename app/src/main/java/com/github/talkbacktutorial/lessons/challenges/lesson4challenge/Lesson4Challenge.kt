package com.github.talkbacktutorial.lessons.challenges.lesson4challenge

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.challenges.lesson2.Lesson2ChallengeActivity
import com.github.talkbacktutorial.activities.challenges.lesson4.Lesson4ChallengeActivity
import com.github.talkbacktutorial.lessons.challenges.Challenge

/**
 * A challenge that makes use of all the content taught in Lesson4 in a practical real-world example.
 * @author Andre Pham
 */
class Lesson4Challenge : Challenge() {

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, Lesson4ChallengeActivity::class.java))
    }
}