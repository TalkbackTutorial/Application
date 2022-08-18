package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.challenges.lesson5challenge.Lesson5Challenge
import com.github.talkbacktutorial.lessons.modules.*

/**
 * Lesson5 teaches the user how to use some operating system related gestures
 * @author Team1
 */
class Lesson5 : Lesson() {

    override val title: String = App.resources.getString(R.string.lesson5_title)
    override val sequenceNumeral: Int = 5
    override val description: String = App.resources.getString(R.string.lesson5_desc)
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            OpenRecentAppsModule(),
            GoToHomeScreenModule(),
            OpenNotificationsModule(),
            OpenTalkbackMenuModule(),
            OpenVoiceCommandModule()
        )
    )
    override val challenge: Challenge = Lesson5Challenge()
}
