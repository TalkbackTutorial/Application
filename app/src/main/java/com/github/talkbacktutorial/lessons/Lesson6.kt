package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.VirtualKeyboardModule

class Lesson6 : Lesson() {

    override val title: String = "Typing Lesson"
    override val sequenceNumeral: Int = 6
    override val description = "These cover the gestures and actions to use a virtual keyboard on your phone"
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            VirtualKeyboardModule()
        )
    )
    override val challenge: Challenge? = null
}
