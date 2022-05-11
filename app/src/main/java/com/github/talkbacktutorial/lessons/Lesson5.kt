package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.*

class Lesson5 : Lesson() {

    override val title: String = "Advanced Menu Navigation"
    override val sequenceNumeral: Int = 5
    override val modules: ArrayList<Module> = ArrayList(listOf(
        GoToHomeScreenModule(),
        OpenNotificationsModule(),
        OpenRecentAppsModule(),
        OpenVoiceCommandModule()
    ))

}