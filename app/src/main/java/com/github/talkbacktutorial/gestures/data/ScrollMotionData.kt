package com.github.talkbacktutorial.gestures.data

import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import kotlin.math.abs

/**
 * Stores and manipulates data relating to a single scroll motion.
 * @author Andre Pham
 */
class ScrollMotionData {

    companion object {
        private const val TRAVEL_THRESHOLD = 6
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

    /**
     * Populate this class with the data of a single scroll motion.
     * @param downMotionEvent The motion event of a finger coming in contact
     * @param upMotionEvent The motion event of a finger losing contact
     * @param pointerCount The number of fingers performing the gesture
     * @author Andre Pham
     */
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

    /**
     * Reset the data this class holds to represent no gesture.
     * @author Andre Pham
     */
    fun reset() {
        this.downMotionCoords.clear()
        this.upMotionCoords.clear()
        this.distanceX = 0.0F
        this.distanceY = 0.0F
        this.pointerCount = 0
        this.travelCount = 0
    }

    /**
     * Determine if each finger is travelling in (somewhat) parallel in the same direction
     * to perform the gesture.
     * @return True if the fingers were aligned in position/direction during the scroll
     * @author Andre Pham
     */
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

    /**
     * Whether or not the performed scroll was horizontal (or vertical).
     * @return True if the horizontal distance travelled was further than the vertical.
     * @author Andre Pham
     */
    fun isHorizontalScroll(): Boolean {
        return abs(this.distanceX) > abs(this.distanceY)
    }

    /**
     * Check to see if the scroll was actually a tap.
     * Otherwise, sometimes taps are detected as scrolls that just travelled very little distance.
     * @return True if this scroll wasn't actually a tap gesture.
     * @author Andre Pham
     */
    fun verifiedNotTap(): Boolean {
        return this.travelCount >= TRAVEL_THRESHOLD
    }

    /**
     * The offset between two points.
     * @param point1 The origin point
     * @param point2 The offset point
     * @return A point, where x is the horizontal offset, and y is the vertical offset
     * @author Andre Pham
     */
    private fun pointOffset(point1: MotionEvent.PointerCoords, point2: MotionEvent.PointerCoords): PointF {
        return PointF(point2.x - point1.x, point2.y - point1.y)
    }

}