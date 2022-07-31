package com.github.talkbacktutorial.gestures

/**
 * An enum class that identifies all Talkback gestures.
 * Source of gestures: https://media.dequeuniversity.com/en/courses/generic/testing-screen-readers/2.0/docs/talkback-images-guide.pdf
 * @author Andre Pham
 */
enum class TalkbackGesture {

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
    DOWN_RIGHT,
    UP_RIGHT,
    TAP_3,

    // Pause or resume reading
    TAP_2,

    // Scroll
    UP_2,
    DOWN_2,
    LEFT_2,
    RIGHT_2,

    // Reading controls
    UP_DOWN,
    DOWN_UP,
    UP_3,
    DOWN_3,
    LEFT_3,
    RIGHT_3,

    // Start reading continuously
    TRIPLE_TAP_2,

    // Travel reading control items
    DOWN,
    UP,

    // Elements
    LEFT,
    RIGHT,
    DOUBLE_TAP,

    // Navigation
    DOWN_LEFT,
    UP_LEFT,
    LEFT_UP,
    RIGHT_DOWN,

    // Media
    DOUBLE_TAP_2,
    RIGHT_LEFT,
    LEFT_RIGHT,

    // Other
    LEFT_DOWN,
    RIGHT_UP,

    // None
    NO_MATCH,

}