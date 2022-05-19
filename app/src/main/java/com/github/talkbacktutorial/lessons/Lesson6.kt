package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.*

class Lesson6 : Lesson() {
    override val title: String = "External Apps"
    override val sequenceNumeral: Int = 6
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            CalculatorAppModule()
        )
    )
}