package com.github.talkbacktutorial.lessons.challenges.lesson6challenge

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.challenges.lesson6.Lesson6ChallengeActivity
import com.github.talkbacktutorial.lessons.challenges.Challenge

/**
 * TODO Edit Documentations
 * @author Natalie Law, Sandy Du & Nabeeb Yusuf
 */
class Lesson6Challenge : Challenge() {

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, Lesson6ChallengeActivity::class.java))
    }
}