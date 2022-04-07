package com.github.talkbacktutorial


class Lesson1 : Lesson() {

    override val title: String = "TODO LESSON 1" // E.g. "Media Interaction"
    override val sequenceNumeral: Int = 1
    override val modules: ArrayList<Module> = ArrayList(listOf(
        TodoModuleX(),
        TodoModuleY()
        // Add more ...
    ))

}