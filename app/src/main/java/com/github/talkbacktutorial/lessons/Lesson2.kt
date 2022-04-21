package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.ExploreMenuByTouchModule
import com.github.talkbacktutorial.lessons.modules.SelectSettingModule
import com.github.talkbacktutorial.lessons.modules.Module
import com.github.talkbacktutorial.lessons.modules.ScrollingModule

/**
 * Presents simulated menus and has the user learn the aspects of simple menu navigation, apart
 * from simple left/right swiping.
 *
 * @author Team2 (Andre Pham, Emmanuel, Antony Loose, Jade Davis, Jason Wu)
 */
class Lesson2 : Lesson() {

    override val title: String = "Basic Menu Navigation"
    override val sequenceNumeral: Int = 2

    override val modules: ArrayList<Module> = ArrayList(listOf(
        ExploreMenuByTouchModule(),
        ScrollingModule(),
        SelectSettingModule()
    ))

}