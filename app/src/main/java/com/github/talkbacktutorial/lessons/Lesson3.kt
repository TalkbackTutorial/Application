package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.*

/**
 * Lesson 3 teaches about how to control how Talkback reads content and how it can also enable the
 * user to more effectively navigate their device.
 * @author Matthew Crossman
 * @author Team 4
 */
class Lesson3 : Lesson() {

    override val title = "Controlling Reading"
    override val sequenceNumeral = 3
    override val modules = ArrayList(
        listOf(
            AdjustReadingControlsModule(),
            JumpTextModule(),
            JumpControlsModule(),
            JumpHeadersModule(),
            JumpLinksModule()
        )
    )
}
