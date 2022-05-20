package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.MediaVolumeControlModule
import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.StartStopMediaModule

/**
 * Lesson4 teaches the user how to interact with media.
 * @author Sandy Du & Natalie Law
 */
class Lesson4 : Lesson() {

    override val title: String = "Interacting with Media"
    override val sequenceNumeral: Int = 4
    override val description: String = "These cover the gestures and action to interact with media"
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            StartStopMediaModule(),
            MediaVolumeControlModule()
        )
    )
}
