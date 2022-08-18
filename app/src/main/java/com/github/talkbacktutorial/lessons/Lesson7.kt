package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.modules.CalculatorAppModule
import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.VoiceRecorderAppModule

/**
 * This lesson covers working within external apps. It involves opening modified versions of third-
 * party apps and performing a set of actions within them.
 *
 * This should be one of the last lessons in the app.
 *
 * @author Team 3
 */
class Lesson7 : Lesson() {

    override val title: String = App.resources.getString(R.string.lesson7_title)
    override val sequenceNumeral: Int = 7
    override val description = App.resources.getString(R.string.lesson7_desc)
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            CalculatorAppModule(),
            VoiceRecorderAppModule()
        )
    )
    override val challenge: Challenge? = null
}