package com.github.talkbacktutorial.lessons

import com.github.talkbacktutorial.lessons.modules.*

/**
 * Presents simulated menus and has the user learn the aspects of simple menu navigation, apart
 * from simple left/right swiping.
 * @author Team2 (Andre Pham, Emmanuel, Antony Loose, Jade Davis, Jason Wu)
 */
class Lesson2 : Lesson() {

    override val title: String = "Basic Menu Navigation"
    override val sequenceNumeral: Int = 2
    //TODO: define description
    override val description: String = "These cover the basics of Android navigation through Talkback"

    override val modules: ArrayList<Module> = ArrayList(
        listOf(
            ExploreMenuByTouchModule(),
            ScrollingModule(),
            GoBackModule(),
            AdjustSliderModule()
        )
    )
}
