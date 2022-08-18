package com.github.talkbacktutorial.accessibilitymanager

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityManager
import com.github.talkbacktutorial.App

/**
 * Manages listeners that trigger when Talkback changes to being either enabled or disabled.
 * @author Andre Pham
 */
object AccessibilityChangeManager {

    private val accessibilityManager = App.context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
    private var page: AccessibilityChangePage = AccessibilityChangePage.MAIN
    private var activeListener: () -> Unit = { }

    /**
     * Set the page that is active. The page determines which listeners are triggered.
     * Call in AppCompatActivity.onResume()
     * @param page The active page
     * @author Andre Pham
     */
    fun setPage(page: AccessibilityChangePage) {
        this.page = page
    }

    /**
     * If a specific page is active, reset it to NONE.
     * This means if a new activity sets a new page before the reset, no issues occur.
     * Call in AppCompatActivity.onStop()
     * @param from The page you're resetting from
     * @author Andre Pham
     */
    fun resetPage(from: AccessibilityChangePage) {
        if (this.page == from) {
            this.page = AccessibilityChangePage.NONE
        }
    }

    /**
     * Add a listener to trigger when Talkback is enabled/disabled. Automatically resets the
     * previous listener so listeners don't endlessly build up or overlap on a certain page.
     * @param listener The listener to trigger
     * @author Andre Pham
     */
    fun setListener(listener: AccessibilityChangeListener) {
        this.accessibilityManager.removeAccessibilityStateChangeListener { this.activeListener }
        this.activeListener = {
            Handler(Looper.getMainLooper()).postDelayed({
                if (this.page == listener.associatedPage) {
                    for (accessibilityServiceInfo in this.accessibilityManager
                        .getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_SPOKEN)
                    ) {
                        if (accessibilityServiceInfo.resolveInfo.serviceInfo.processName.contains("talkback", ignoreCase = true)) { // Check keyword as different device give different processName
                            listener.talkbackOnCallback()
                            return@postDelayed
                        }
                    }
                    listener.talkbackOffCallback()
                }
            }, 200) // Set time delay to ensure accessibilityServiceInfo is changed after turn on/off talkback
        }
        this.accessibilityManager.addAccessibilityStateChangeListener {
            this.activeListener()
        }
    }

}