package com.github.talkbacktutorial.gestures

/**
 * An enum class that identifies all Talkback gestures.
 * Source of gestures: https://media.dequeuniversity.com/en/courses/generic/testing-screen-readers/2.0/docs/talkback-images-guide.pdf
 * @author Andre Pham
 */
enum class TalkbackGesture(val action: TalkbackAction, val description: String) {

    /**
     * An overview on how to interpret these names.
     *
     * UP/DOWN/LEFT/RIGHT are swipe directions.
     * - DOWN = swipe down
     * - DOWN_RIGHT = swipe down, then right, without losing contact with the screen
     * - LEFT_RIGHT = swipe left, then right, without losing contact with the screen
     *
     * X_TAP are taps. X is the number of taps.
     * - DOUBLE_TAP = a double tap with a single finger
     *
     * Digits are the number of fingers.
     * - TAP_2 = a single tap with two fingers (two fingers tap the screen once)
     * - TRIPLE_TAP_2 = a triple tap with two fingers (two fingers tap the screen three times)
     * - UP_2 = swipe up using two fingers
     */

    // Open the TalkBack menu
    DOWN_RIGHT(TalkbackAction.OPEN_TALKBACK_MENU, "Swipe down then right in one motion"),
    UP_RIGHT(TalkbackAction.OPEN_TALKBACK_MENU, "Swipe up then right in one motion"),
    TAP_3(TalkbackAction.OPEN_TALKBACK_MENU, "Tap with three fingers"),

    // Pause or resume reading
    TAP_2(TalkbackAction.PAUSE_RESUME_READING, "Tap with two fingers"),

    // Scroll
    UP_2(TalkbackAction.SCROLL_DOWN, "Swipe up with two fingers"),
    DOWN_2(TalkbackAction.SCROLL_UP, "Swipe down with two fingers"),
    LEFT_2(TalkbackAction.SCROLL_RIGHT, "Swipe left with two fingers"),
    RIGHT_2(TalkbackAction.SCROLL_LEFT, "Swipe right with two fingers"),

    // Reading controls
    UP_DOWN(TalkbackAction.NEXT_READING_CONTROL, "Swipe up then down in one motion"),
    DOWN_UP(TalkbackAction.PREVIOUS_READING_CONTROL, "Swipe down then up in one motion"),
    UP_3(TalkbackAction.NEXT_READING_CONTROL, "Swipe up with three fingers"),
    DOWN_3(TalkbackAction.PREVIOUS_READING_CONTROL, "Swipe down with three fingers"),
    LEFT_3(TalkbackAction.NEXT_READING_CONTROL, "Swipe left with three fingers"),
    RIGHT_3(TalkbackAction.PREVIOUS_READING_CONTROL, "Swipe right with three fingers"),

    // Start reading continuously
    TRIPLE_TAP_2(TalkbackAction.READ_CONTINUOUSLY, "Triple tap with two fingers"),

    // Travel reading control items
    DOWN(TalkbackAction.PREVIOUS_READING_CONTROL_ITEM, "Swipe down"),
    UP(TalkbackAction.NEXT_READING_CONTROL_ITEM, "Swipe up"),

    // Elements
    LEFT(TalkbackAction.PREVIOUS_ITEM, "Swipe left"),
    RIGHT(TalkbackAction.NEXT_ITEM, "Swipe right"),
    DOUBLE_TAP(TalkbackAction.INTERACT_ITEM, "Double tap"),

    // Navigation
    DOWN_LEFT(TalkbackAction.BACK, "Swipe down then left in one motion"),
    UP_LEFT(TalkbackAction.HOME_SCREEN, "Swipe up then left in one motion"),
    LEFT_UP(TalkbackAction.RECENT_APPS, "Swipe left then up in one motion"),
    RIGHT_DOWN(TalkbackAction.NOTIFICATION_PANEL, "Swipe right then down in one motion"),

    // Media
    DOUBLE_TAP_2(TalkbackAction.MEDIA_OR_CALL, "Double tap with two fingers"),
    RIGHT_LEFT(TalkbackAction.SLIDER_INCREASE, "Swipe right then left in one motion"),
    LEFT_RIGHT(TalkbackAction.SLIDER_DECREASE, "Swipe left then right in one motion"),

    // Other
    LEFT_DOWN(TalkbackAction.TEXT_SEARCH, "Swipe left then down in one motion"),
    RIGHT_UP(TalkbackAction.VOICE_COMMANDS, "Swipe right then up in one motion"),

    // None
    NO_MATCH(TalkbackAction.NONE, ""),
    TAP(TalkbackAction.NONE, "");

    val actionDescription: String
        get() { return this.action.description }

    /**
     * If this gesture represents a fling.
     * @return True if a fling generates this gesture.
     * @author Andre Pham
     */
    fun isFlingGesture(): Boolean {
        return when (this) {
            DOWN_RIGHT -> true
            UP_RIGHT -> true
            TAP_3 -> false
            TAP_2 -> false
            UP_2 -> true
            DOWN_2 -> true
            LEFT_2 -> true
            RIGHT_2 -> true
            UP_DOWN -> true
            DOWN_UP -> true
            UP_3 -> true
            DOWN_3 -> true
            LEFT_3 -> true
            RIGHT_3 -> true
            TRIPLE_TAP_2 -> false
            DOWN -> true
            UP -> true
            LEFT -> true
            RIGHT -> true
            DOUBLE_TAP -> false
            DOWN_LEFT -> true
            UP_LEFT -> true
            LEFT_UP -> true
            RIGHT_DOWN -> true
            DOUBLE_TAP_2 -> false
            RIGHT_LEFT -> true
            LEFT_RIGHT -> true
            LEFT_DOWN -> true
            RIGHT_UP -> true
            NO_MATCH -> false
            TAP -> false
        }
    }

    /**
     * If this gesture represents a tap.
     * @return True if tap(s) generates this gesture.
     * @author Andre Pham
     */
    fun isTapGesture(): Boolean {
        if (this == NO_MATCH) {
            return false
        }
        return !this.isFlingGesture()
    }

    /**
     * Checks if two gestures perform the same action.
     * @param gesture the gesture to check against
     * @return True if the gesture action descriptions match
     * @author Andre Pham
     */
    fun gestureActionMatches(gesture: TalkbackGesture): Boolean {
        return this.action == gesture.action
    }

}