package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.*

class Lesson6 : Lesson() {

    override val title: String = "Typing Lesson"
    override val sequenceNumeral: Int = 6
    override val description = "These cover the gestures and actions to use a virtual keyboard on the phone"
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            VirtualKeyboardModule()
        )
    )
}
