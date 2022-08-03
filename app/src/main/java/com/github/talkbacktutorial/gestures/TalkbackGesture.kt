package com.github.talkbacktutorial.gestures

/**
 * An enum class that identifies all Talkback gestures.
 * Source of gestures: https://media.dequeuniversity.com/en/courses/generic/testing-screen-readers/2.0/docs/talkback-images-guide.pdf
 * @author Andre Pham
 */
enum class TalkbackGesture(val description: String) {

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
    DOWN_RIGHT("Open the talkback menu"),
    UP_RIGHT("Open the talkback menu"),
    TAP_3("Open the talkback menu"),

    // Pause or resume reading
    TAP_2("Pause or resume reading"),

    // Scroll
    UP_2("Scroll down"),
    DOWN_2("Scroll up"),
    LEFT_2("Scroll right"),
    RIGHT_2("Scroll left"),

    // Reading controls
    UP_DOWN("Change to next reading control"),
    DOWN_UP("Change to previous reading control"),
    UP_3("Change to next reading control"),
    DOWN_3("Change to previous reading control"),
    LEFT_3("Change to next reading control"),
    RIGHT_3("Change to previous reading control"),

    // Start reading continuously
    TRIPLE_TAP_2("Read continuously from this point"),

    // Travel reading control items
    DOWN("Travel reading control items"),
    UP("Travel reading control items"),

    // Elements
    LEFT("Read previous item"),
    RIGHT("Read next item"),
    DOUBLE_TAP("Activate element"), // TODO: use better word than activate

    // Navigation
    DOWN_LEFT("Go back"),
    UP_LEFT("Go to home screen"),
    LEFT_UP("Show recent apps"),
    RIGHT_DOWN("Show notifications"),

    // Media
    DOUBLE_TAP_2("Start or stop media. Or answer or end call"),
    RIGHT_LEFT("Increase slider"),
    LEFT_RIGHT("Decrease slider"),

    // Other
    LEFT_DOWN("Search for a word or phrase"),
    RIGHT_UP("Open voice commands"),

    // None
    NO_MATCH("");

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

}