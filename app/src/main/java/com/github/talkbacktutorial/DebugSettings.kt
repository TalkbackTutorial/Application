package com.github.talkbacktutorial

/**
 * Used to enable or disable certain parts of the application during development.
 * Change these how you please on your local machine.
 * Don't push changes you make to settings within this class.
 * @author Andre Pham
 */
object DebugSettings {

    // Unlocks all lessons, modules and challenges if true
    val bypassProgressionLocks
        get() = !BuildConfig.PROGRESS_LOCK

    // Talkback isn't required if true
    val talkbackNotRequired
        get() = !BuildConfig.TB_REQ

    // Skip introductory lesson
    val skipIntroductoryLesson
        get() = BuildConfig.SKIP_INTRO

    // Skip Text to Speech
    val skipTTS = false

    // Wipes database and closes app if true
    val wipeDatabase = false
}