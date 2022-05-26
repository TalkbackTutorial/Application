package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.modules.AdjustReadingControlsModule
import com.github.talkbacktutorial.lessons.modules.JumpControlsModule
import com.github.talkbacktutorial.lessons.modules.JumpHeadersModule
import com.github.talkbacktutorial.lessons.modules.JumpTextModule
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
    override val modules = ArrayList(
        listOf(
            AdjustReadingControlsModule(),
            JumpTextModule(),
            JumpControlsModule(),
            JumpHeadersModule()
        )
    )
    override val challenge: Challenge = Lesson3Challenge()
}
