package com.github.talkbacktutorial.gamemode

import com.github.talkbacktutorial.gestures.TalkbackGesture

class GesturePool {

    private var gestures = ArrayList<TalkbackGesture>()

    init {
        fillPool()
    }

    /**
     * Refill the pool
     * @author Antony Loose, Andre Pham
     */
    private fun fillPool() {
        val newPool = TalkbackGesture.values().toMutableList()
        newPool.removeAll { it.gestureAction() == null } // Remove gestures such as NO_MATCH
        newPool.shuffle() // Must shuffle before removing duplicates
        gestures = ArrayList(newPool.distinctBy { it.gestureAction() })
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