package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.SelectSettingModule
import java.util.ArrayList

class LessonTemp6 : Lesson() {
    override val title: String = "Changing Voice Reading Settings"
    override val sequenceNumeral: Int = 6
    override val modules: ArrayList<Module> = ArrayList(listOf(
        SelectSettingModule()
    ))
}