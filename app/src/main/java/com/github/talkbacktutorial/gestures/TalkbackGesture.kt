package com.github.talkbacktutorial.gestures

/**
 * An enum class that identifies all Talkback gestures.
 * Source of gestures: https://media.dequeuniversity.com/en/courses/generic/testing-screen-readers/2.0/docs/talkback-images-guide.pdf
 * @author Andre Pham
 */
enum class TalkbackGesture(val action: TalkbackAction) {

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
    DOWN_RIGHT(TalkbackAction.OPEN_TALKBACK_MENU),
    UP_RIGHT(TalkbackAction.OPEN_TALKBACK_MENU),
    TAP_3(TalkbackAction.OPEN_TALKBACK_MENU),

    // Pause or resume reading
    TAP_2(TalkbackAction.PAUSE_RESUME_READING),

    // Scroll
    UP_2(TalkbackAction.SCROLL_DOWN),
    DOWN_2(TalkbackAction.SCROLL_UP),
    LEFT_2(TalkbackAction.SCROLL_RIGHT),
    RIGHT_2(TalkbackAction.SCROLL_LEFT),

    // Reading controls
    UP_DOWN(TalkbackAction.NEXT_READING_CONTROL),
    DOWN_UP(TalkbackAction.PREVIOUS_READING_CONTROL),
    UP_3(TalkbackAction.NEXT_READING_CONTROL),
    DOWN_3(TalkbackAction.PREVIOUS_READING_CONTROL),
    LEFT_3(TalkbackAction.NEXT_READING_CONTROL),
    RIGHT_3(TalkbackAction.PREVIOUS_READING_CONTROL),

    // Start reading continuously
    TRIPLE_TAP_2(TalkbackAction.READ_CONTINUOUSLY),

    // Travel reading control items
    DOWN(TalkbackAction.PREVIOUS_READING_CONTROL_ITEM),
    UP(TalkbackAction.NEXT_READING_CONTROL_ITEM),

    // Elements
    LEFT(TalkbackAction.PREVIOUS_ITEM),
    RIGHT(TalkbackAction.NEXT_ITEM),
    DOUBLE_TAP(TalkbackAction.INTERACT_ITEM),

    // Navigation
    DOWN_LEFT(TalkbackAction.BACK),
    UP_LEFT(TalkbackAction.HOME_SCREEN),
    LEFT_UP(TalkbackAction.RECENT_APPS),
    RIGHT_DOWN(TalkbackAction.NOTIFICATION_PANEL),

    // Media
    DOUBLE_TAP_2(TalkbackAction.MEDIA_OR_CALL),
    RIGHT_LEFT(TalkbackAction.SLIDER_INCREASE),
    LEFT_RIGHT(TalkbackAction.SLIDER_DECREASE),

    // Other
    LEFT_DOWN(TalkbackAction.TEXT_SEARCH),
    RIGHT_UP(TalkbackAction.VOICE_COMMANDS),

    // None
    NO_MATCH(TalkbackAction.NONE);

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