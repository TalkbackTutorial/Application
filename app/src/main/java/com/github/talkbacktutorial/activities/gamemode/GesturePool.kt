package com.github.talkbacktutorial.activities.gamemode

import com.github.talkbacktutorial.gestures.TalkbackGesture
import kotlin.random.Random

class GesturePool (
    var gestures: MutableList<TalkbackGesture> = TalkbackGesture.values().toMutableList()
    ) {

    init {
        fillPool()
    }

    /**
     * Refill the pool
     * @author Antony Loose
     */
    fun fillPool(){
        gestures = TalkbackGesture.values().toMutableList()
    }

    /**
     * Takes a gesture from the pool and removes any gestures with the same action, if the pool is empty
     * then we refill
     * @author Antony Loose
     */
    fun takeGesture(): TalkbackGesture{
        // get random gesture
        val randIndex = Random.nextInt(gestures.size)
        val randGesture = gestures[randIndex]

        // remove gestures with the same actions
        for ((i, gesture) in gestures.withIndex()){
            if (gesture.gestureAction() == randGesture.gestureAction()){
                // TODO: this causes an error as i am trying to remove using removeAt within an iterator
                // gestures.removeAt(i)
            }
        }

        // refill if empty
        if (gestures.size == 0){
            fillPool()
        }

        return randGesture
    }
}