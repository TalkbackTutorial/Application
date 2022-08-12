package com.github.talkbacktutorial

/**
 * Used to enable or disable certain parts of the application during development.
 * Change these how you please on your local machine.
 * Don't push changes you make to settings within this class.
 * @author Andre Pham
 */
object DebugSettings {

    // Unlocks all lessons, modules and challenges if true
    const val bypassProgressionLocks = true

    // Talkback isn't required if true
    const val talkbackNotRequired = false

    // Skip introductory lesson
    var skipIntroductoryLesson = false

    // Skip Text to Speech
    const val skipTTS = false

    // Wipes database and closes app if true
    const val wipeDatabase = false
}