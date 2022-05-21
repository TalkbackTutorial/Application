package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.*
import java.util.ArrayList

class Lesson7 : Lesson() {
    override val title: String = "Typing Using Braille Keyboard"
    override val sequenceNumeral: Int = 7
    override val modules: ArrayList<Module> = ArrayList(
            listOf(
                SubmitTextModule(),
                HideBrailleKeyboardModule(),
                OpenBrailleKeyboardSettingsModule()
            )
    )
}