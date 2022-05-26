package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.modules.CalculatorAppModule
import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.VoiceRecorderAppModule

class Lesson8 : Lesson() {

    override val title: String = "External Apps"
    override val sequenceNumeral: Int = 8
    override val description = "These provide real world experience by having you jump to other applications"
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            CalculatorAppModule(),
            VoiceRecorderAppModule()
        )
    )
    override val challenge: Challenge? = null
}