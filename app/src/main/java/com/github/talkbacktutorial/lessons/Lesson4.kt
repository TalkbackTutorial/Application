package com.github.talkbacktutorial.lessons

import android.content.Context
import android.content.Intent
import com.github.talkbacktutorial.activities.lesson0.Lesson0Activity
import com.github.talkbacktutorial.lessons.modules.MediaVolumeControlModule
import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.StartStopMediaModule

/**
 * Lesson4 will teach the user how to interact with media.
 * @author Sandy Du & Natalie Law
 */

class Lesson4: Lesson() {

    override val title: String = "Interacting with Media"
    override val sequenceNumeral: Int = 4
    override val modules: ArrayList<Module> = ArrayList(listOf(
        StartStopMediaModule(),
        MediaVolumeControlModule()
    ))
}