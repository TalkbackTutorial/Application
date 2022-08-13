package com.github.talkbacktutorial.accessibilitymanager

/**
 * Stores information and callbacks relating to a detection of Talkback being turned on/off.
 * @author Andre Pham
 */
class AccessibilityChangeListener(
    val associatedPage: AccessibilityChangePage,
    val talkbackOnCallback: () -> Unit,
    val talkbackOffCallback: () -> Unit
)