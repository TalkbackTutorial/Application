package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.challenges.lesson6challenge.Lesson6Challenge
import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.VirtualKeyboardModule

/**
 * Lesson6 teaches the user how to open and type using the virtual/on-screen keyboard
 * @author Team4
 */
class Lesson6 : Lesson() {

    override val title: String = App.resources.getString(R.string.lesson6_title)
    override val sequenceNumeral: Int = 6
    override val description = App.resources.getString(R.string.lesson6_desc)
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            VirtualKeyboardModule()
        )
    )
    override val challenge: Challenge = Lesson6Challenge()
}
