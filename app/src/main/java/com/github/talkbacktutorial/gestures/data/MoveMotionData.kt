package com.github.talkbacktutorial.gestures.data

import android.graphics.PointF
import android.view.MotionEvent

class MoveMotionData {

    private var moveMotionPoints: ArrayList<PointF> = ArrayList()

    fun pullDataFrom(event: MotionEvent) {
        this.moveMotionPoints.add(PointF(event.x, event.y))
    }

    fun clear() {
        this.moveMotionPoints.clear()
    }

    override fun toString(): String {
        return this.moveMotionPoints.joinToString(separator = ", ") {
            it.toString()
        }
    }

}