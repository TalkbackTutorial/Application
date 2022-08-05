package com.github.talkbacktutorial.gestures

enum class TalkbackAction(val description: String) {

    OPEN_TALKBACK_MENU("Open the talkback menu"),
    PAUSE_RESUME_READING("Pause or resume reading"),
    SCROLL_DOWN("Scroll down"),
    SCROLL_UP("Scroll up"),
    SCROLL_RIGHT("Scroll right"),
    SCROLL_LEFT("Scroll left"),
    NEXT_READING_CONTROL("Change to next reading control"),
    PREVIOUS_READING_CONTROL("Change to previous reading control"),
    READ_CONTINUOUSLY("Read continuously from this point"),
    PREVIOUS_READING_CONTROL_ITEM("Jump to previous reading control item"),
    NEXT_READING_CONTROL_ITEM("Jump to next reading control item"),
    PREVIOUS_ITEM("Move to previous item"),
    NEXT_ITEM("Move to next item"),
    INTERACT_ITEM("Interact with item"),
    BACK("Go back"),
    HOME_SCREEN("Go to home screen"),
    RECENT_APPS("Show recent apps"),
    NOTIFICATION_PANEL("Show notification panel"),
    MEDIA_OR_CALL("Start or stop media. Answer or end a call"),
    SLIDER_INCREASE("Increase slider"),
    SLIDER_DECREASE("Decrease slider"),
    TEXT_SEARCH("Screen text search"),
    VOICE_COMMANDS("Open voice commands"),
    NONE("");

}