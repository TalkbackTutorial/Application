package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.R
import com.github.talkbacktutorial.lessons.modules.AdjustReadingControls
import com.github.talkbacktutorial.lessons.modules.Module
import java.util.ArrayList

class Lesson3 : Lesson() {
    override val title = "Controlling Reading"
    override val sequenceNumeral = 3
    override val modules = ArrayList<Module>(listOf(
        AdjustReadingControls()
    ))
}