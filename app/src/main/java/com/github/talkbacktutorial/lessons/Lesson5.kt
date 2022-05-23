package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.challenges.lesson2challenge.Lesson2Challenge
import com.github.talkbacktutorial.lessons.challenges.lesson5challenge.Lesson5Challenge
import com.github.talkbacktutorial.lessons.modules.*

class Lesson5 : Lesson() {

    override val title: String = "Advanced Menu Navigation"
    override val sequenceNumeral: Int = 5
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            GoToHomeScreenModule(),
            OpenNotificationsModule(),
            OpenRecentAppsModule(),
            OpenVoiceCommandModule()
        )
    )
    override val challenge: Challenge = Lesson5Challenge()
}
