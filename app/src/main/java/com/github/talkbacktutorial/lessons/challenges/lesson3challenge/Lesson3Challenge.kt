package com.github.talkbacktutorial.lessons.challenges.lesson3challenge

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.challenges.lesson3.Lesson3ChallengeActivity
import com.github.talkbacktutorial.lessons.challenges.Challenge

class Lesson3Challenge : Challenge() {

    override fun startActivity(context: Context) {
        context.startActivity(Intent(context, Lesson3ChallengeActivity::class.java))
    }
}