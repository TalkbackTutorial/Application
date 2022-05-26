package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.*
import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.challenges.lesson3challenge.Lesson3Challenge

/**
 * Lesson 3 teaches about how to control how Talkback reads content and how it can also enable the
 * user to more effectively navigate their device.
 * @author Matthew Crossman
 * @author Team 4
 */
class Lesson3 : Lesson() {

    override val title = "Controlling Reading"
    override val sequenceNumeral = 3
    override val description: String = "These cover the gestures and actions to perform to change the read settings of Talkback"
    override val modules = ArrayList(
        listOf(
            AdjustReadingControlsModule(),
            JumpTextModule(),
            JumpControlsModule(),
            JumpHeadersModule(),
            JumpLinksModule()
        )
    )
    override val challenge: Challenge = Lesson3Challenge()
}
