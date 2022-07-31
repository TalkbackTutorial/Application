package com.github.talkbacktutorial.gestures.data

import android.graphics.PointF
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import kotlin.math.abs
import kotlin.math.max

class ScrollMotionData {

    companion object {
        private const val TRAVEL_THRESHOLD = 10
    }

    private val downMotionCoords = MotionEvent.PointerCoords()
    private val upMotionCoords = ArrayList<MotionEvent.PointerCoords>()
    var distanceX: Float = 0.0F
        private set
    var distanceY: Float = 0.0F
        private set
    var pointerCount: Int = 0
        private set
    private var travelCount = 0

    fun setData(
        downMotionEvent: MotionEvent?,
        upMotionEvent: MotionEvent?,
        pointerCount: Int
    ) {
        this.travelCount += 1
        if (pointerCount >= this.pointerCount) {
            downMotionEvent?.getPointerCoords(0, this.downMotionCoords)
            this.upMotionCoords.clear()
            for (index in 0 until pointerCount) {
                this.upMotionCoords.add(MotionEvent.PointerCoords())
                upMotionEvent?.getPointerCoords(index, this.upMotionCoords.last())
            }
            this.distanceX = this.pointOffset(this.downMotionCoords, this.upMotionCoords[0]).x
            this.distanceY = this.pointOffset(this.downMotionCoords, this.upMotionCoords[0]).y
            this.pointerCount = pointerCount
        }
    }

    fun reset() {
        this.downMotionCoords.clear()
        this.upMotionCoords.clear()
        this.distanceX = 0.0F
        this.distanceY = 0.0F
        this.pointerCount = 0
        this.travelCount = 0
    }

    fun pointersAreAligned(): Boolean {
        if (this.pointerCount == 1) {
            return true
        }
        val offsets: ArrayList<PointF> = ArrayList()
        for (coords in this.upMotionCoords) {
            offsets.add(this.pointOffset(this.downMotionCoords, coords))
            // This isn't really ideal, but there isn't really another option
            // There's no way that I can see where we can track more than a single "down" pointer
            if (this.isHorizontalScroll()) {
                offsets.last().y = 0.0F
            } else {
                offsets.last().x = 0.0F
            }
        }
        var pointsAreAligned = true
        if (this.isHorizontalScroll()) {
            if (this.distanceX > 0) {
                // Right
                for (offset in offsets) {
                    val conforms = abs(offset.x) > abs(offset.y) && offset.x > 0
                    if (!conforms) {
                        pointsAreAligned = false
                    }
                }
            } else {
                // Left
                for (offset in offsets) {
                    val conforms = abs(offset.x) > abs(offset.y) && offset.x < 0
                    if (!conforms) {
                        pointsAreAligned = false
                    }
                }
            }
        } else {
            if (this.distanceY > 0) {
                // Down
                for (offset in offsets) {
                    val conforms = abs(offset.x) < abs(offset.y) && offset.y > 0
                    if (!conforms) {
                        pointsAreAligned = false
                    }
                }
            } else {
                // Up
                for (offset in offsets) {
                    val conforms = abs(offset.x) < abs(offset.y) && offset.y < 0
                    if (!conforms) {
                        pointsAreAligned = false
                    }
                }
            }
        }
        return pointsAreAligned
    }

    fun isHorizontalScroll(): Boolean {
        return abs(this.distanceX) > abs(this.distanceY)
    }

    fun verifiedNotTap(): Boolean {
        return this.travelCount >= TRAVEL_THRESHOLD
    }

    private fun pointOffset(point1: MotionEvent.PointerCoords, point2: MotionEvent.PointerCoords): PointF {
        return PointF(point2.x - point1.x, point2.y - point1.y)
    }

}