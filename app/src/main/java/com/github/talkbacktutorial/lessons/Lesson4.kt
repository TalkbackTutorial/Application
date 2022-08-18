package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.App
import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.lessons.challenges.Challenge
import com.github.talkbacktutorial.lessons.challenges.lesson4challenge.Lesson4Challenge
import com.github.talkbacktutorial.lessons.modules.MediaVolumeControlModule
import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.StartStopMediaModule

/**
 * Lesson4 teaches the user how to interact with media.
 * @author Sandy Du & Natalie Law
 * @author Andre Pham (challenge component)
 */
class Lesson4 : Lesson() {

    override val title: String = App.resources.getString(R.string.lesson4_title)
    override val sequenceNumeral: Int = 4
    override val description: String = App.resources.getString(R.string.lesson4_desc)
    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            StartStopMediaModule(),
            MediaVolumeControlModule()
        )
    )
    override val challenge: Challenge = Lesson4Challenge()
}
