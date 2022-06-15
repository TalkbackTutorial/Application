package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.modules.HideBrailleKeyboardModule
import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.OpenBrailleKeyboardSettingsModule
import com.github.talkbacktutorial.lessons.modules.SubmitTextModule

class Lesson7 : Lesson() {

    override val title: String = "Typing Using Braille Keyboard"
    override val sequenceNumeral: Int = 7
    override val description = "These cover the gestures and actions to use the braille keyboard on your phone"
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            SubmitTextModule(),
            HideBrailleKeyboardModule(),
            OpenBrailleKeyboardSettingsModule()
        )
    )
    override val challenge: Challenge? = null
}