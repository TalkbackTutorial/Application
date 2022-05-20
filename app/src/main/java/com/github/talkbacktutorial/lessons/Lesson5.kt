package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.*

class Lesson5 : Lesson() {

    override val title: String = "Advanced Menu Navigation"
    override val sequenceNumeral: Int = 5
    //TODO: define description
    override val description: String = "These cover the gestures and actions to perform navigation in an advance menu"
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            GoToHomeScreenModule(),
            OpenNotificationsModule(),
            OpenRecentAppsModule(),
            OpenVoiceCommandModule()
        )
    )
}
