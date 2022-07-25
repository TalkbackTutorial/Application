package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.challenges.lesson3challenge.Lesson3Challenge
import com.github.talkbacktutorial.lessons.modules.*

/**
 * Lesson 3 teaches about how to control how Talkback reads content and how it can also enable the
 * user to more effectively navigate their device.
 * @author Matthew Crossman
 * @author Team 4
 */
class Lesson3 : Lesson() {

    override val title = App.resources.getString(R.string.lesson3_title)
    override val sequenceNumeral = 3
    override val description: String = App.resources.getString(R.string.lesson3_desc)
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
