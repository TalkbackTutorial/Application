package com.github.talkbacktutorial.lessons.challenges.lesson6challenge

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.challenges.lesson6.Lesson6ChallengeActivity
import com.github.talkbacktutorial.lessons.challenges.Challenge

/**
 * Lesson 6 challenge uses the ability to open the virtual keyboard and type taught in lesson 6 to
 * challenge the user to type a message in a texting-like app.
 * @author Natalie Law, Sandy Du & Nabeeb Yusuf
 */
class Lesson6Challenge : Challenge() {

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, Lesson6ChallengeActivity::class.java))
    }
}