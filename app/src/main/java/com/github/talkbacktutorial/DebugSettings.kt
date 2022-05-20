package com.github.talkbacktutorial

/**
 * Used to enable or disable certain parts of the application during development.
 * Change these how you please on your local machine.
 * Don't push changes you make to settings within this class.
 * @author Andre Pham
 */
object DebugSettings {

    // Unlocks all lessons if true
    const val bypassLessonLocks = false

    // Unlocks all modules if true
    const val bypassModuleLocks = false

    // Talkback isn't required if true
    const val talkbackNotRequired = false

}