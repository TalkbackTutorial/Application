package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.ExploreMenuByTouchModule
import com.github.talkbacktutorial.lessons.modules.Module

/**
 * TODO
 */
class Lesson2 : Lesson() {

    override val title: String = "TODO LESSON 2" // E.g. "Media Interaction"
    override val sequenceNumeral: Int = 2
    override val modules: ArrayList<Module> = ArrayList(listOf(
        // Add more ...
        ExploreMenuByTouchModule()
    ))

}