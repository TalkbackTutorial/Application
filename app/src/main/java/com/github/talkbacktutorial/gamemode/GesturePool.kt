package com.github.talkbacktutorial.gamemode

import com.github.talkbacktutorial.gestures.TalkbackAction
import com.github.talkbacktutorial.gestures.TalkbackGesture

/**
 * Represents a "pool" of gestures. You can take a gesture out of the pool.
 * If the pool becomes empty, it refills itself.
 * Used to represent a queue of randomised gestures with non-overlapping actions.
 * @author Andre Pham
 */
class GesturePool {

    private var gestures = ArrayList<TalkbackGesture>()

    init {
        fillPool()
    }

    /**
     * Refill the pool
     * @author Andre Pham
     */
    private fun fillPool() {
        val newPool = TalkbackGesture.values().toMutableList()
        newPool.removeAll { it.action == TalkbackAction.NONE } // Remove gestures such as NO_MATCH
        newPool.shuffle() // Must shuffle before removing duplicates
        gestures = ArrayList(newPool.distinctBy { it.action }) // Remove duplicate actions
    }

    /**
     * Takes a gesture from the pool and refill if necessary
     * @author Andre Pham
     */
    fun takeGesture(): TalkbackGesture {
        val gesture = this.gestures.removeLast()
        if (gestures.isEmpty()) {
            fillPool()
        }
        return gesture
    }
}