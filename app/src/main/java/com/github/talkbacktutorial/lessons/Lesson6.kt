package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.VirtualKeyboardModule

class Lesson6 : Lesson() {

    override val title: String = App.resources.getString(R.string.lesson6_title)
    override val sequenceNumeral: Int = 6
    override val description = App.resources.getString(R.string.lesson6_desc)
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            VirtualKeyboardModule()
        )
    )
    override val challenge: Challenge? = null
}
