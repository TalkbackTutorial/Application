package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.modules.HideBrailleKeyboardModule
import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.OpenBrailleKeyboardSettingsModule
import com.github.talkbacktutorial.lessons.modules.SubmitTextModule

class Lesson7 : Lesson() {

    override val title: String = App.resources.getString(R.string.lesson7_title)
    override val sequenceNumeral: Int = 7
    override val description = App.resources.getString(R.string.lesson7_desc)
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            SubmitTextModule(),
            HideBrailleKeyboardModule(),
            OpenBrailleKeyboardSettingsModule()
        )
    )
    override val challenge: Challenge? = null
}