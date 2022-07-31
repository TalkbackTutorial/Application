package com.github.talkbacktutorial.gestures.data

import android.graphics.PointF
import android.view.MotionEvent
import kotlin.math.abs

/**
 * Stores and manipulates data relating to a single fling motion.
 * @author Andre Pham
 */
class FlingMotionData {

    private var downMotionEvent: MotionEvent? = null
    private var upMotionEvent: MotionEvent? = null
    private var velocityX: Float = 0.0F
    private var velocityY: Float = 0.0F

    /**
     * Populate this class with the data of a single fling motion.
     * @param downMotionEvent The motion event of a finger coming in contact
     * @param upMotionEvent The motion event of a finger losing contact
     * @param velocityX The horizontal velocity, where negative represents left
     * @param velocityY The vertical velocity, where negative represents up
     * @author Andre Pham
     */
    fun setData(
        downMotionEvent: MotionEvent?,
        upMotionEvent: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ) {
        this.downMotionEvent = downMotionEvent
        this.upMotionEvent = upMotionEvent
        this.velocityX = velocityX
        this.velocityY = velocityY
    }

    /**
     * Determines if the current fling data can be used.
     * @return If the motion events are null
     * @author Andre Pham
     */
    fun dataIsValid(): Boolean {
        return downMotionEvent != null && upMotionEvent != null
    }

    /**
     * Retrieves the horizontal velocity.
     * @param abs If the velocity should be absolute, or allow negatives to indicate left
     * @return The horizontal velocity
     * @author Andre Pham
     */
    fun xVelocity(abs: Boolean): Float {
        return if (abs) abs(velocityX) else velocityX
    }

    /**
     * Retrieves the vertical velocity.
     * @param abs If the velocity should be absolute, or allow negatives to indicate up
     * @return The vertical velocity
     * @author Andre Pham
     */
    fun yVelocity(abs: Boolean): Float {
        return if (abs) abs(velocityY) else velocityY
    }

    /**
     * Retrieves the horizontal offset between the points the finger came in and out of
     * contact with the screen.
     * @return The horizontal offset
     * @author Andre Pham
     */
    fun xOffset(): Float {
        if (dataIsValid()) {
            return upMotionEvent!!.x - downMotionEvent!!.x
        }
        return 0.0F
    }

    /**
     * Retrieves the horizontal distance between the points the finger came in and out of
     * contact with the screen.
     * @return The horizontal distance (always positive)
     * @author Andre Pham
     */
    fun xDistance(): Float {
        return abs(xOffset())
    }

    /**
     * Retrieves the vertical offset between the points the finger came in and out of
     * contact with the screen.
     * @return The vertical offset
     * @author Andre Pham
     */
    fun yOffset(): Float {
        if (dataIsValid()) {
            return upMotionEvent!!.y - downMotionEvent!!.y
        }
        return 0.0F
    }

    /**
     * Retrieves the vertical distance between the points the finger came in and out of
     * contact with the screen.
     * @return The vertical distance (always positive)
     * @author Andre Pham
     */
    fun yDistance(): Float {
        return abs(yOffset())
    }

    /**
     * Determines if the finger ends in a vertically higher position on the screen post-fling.
     * @return If the finger ends higher, or null if data is invalid
     * @author Andre Pham
     */
    fun flingEndsHigher(): Boolean? {
        if (dataIsValid()) {
            return this.yOffset() < 0
        }
        return null
    }

    /**
     * Determines if the finger ends in a horizontally further (right) position on the
     * screen post-fling.
     * @return If the finger ends further right, or null if data is invalid
     * @author Andre Pham
     */
    fun flingEndsFurther(): Boolean? {
        if (dataIsValid()) {
            return this.xOffset() > 0
        }
        return null
    }

    /**
     * The point at which the finger comes in contact with the screen.
     * @return The fling starting point, or null if the data is invalid
     * @author Andre Pham
     */
    fun downPoint(): PointF? {
        if (dataIsValid()) {
            return PointF(this.downMotionEvent!!.x, this.downMotionEvent!!.y)
        }
        return null
    }

    /**
     * The point at which the finger leaves contact with the screen.
     * @return The fling ending point, or null if the data is invalid
     * @author Andre Pham
     */
    fun upPoint(): PointF? {
        if (dataIsValid()) {
            return PointF(this.upMotionEvent!!.x, this.upMotionEvent!!.y)
        }
        return null
    }

    override fun toString(): String {
        val downCoords: String = "ACTION_DOWN Point: " + downMotionEvent?.x.toString() + ", " + downMotionEvent?.y.toString()
        val upCoords: String = "ACTION_UP Point: " + upMotionEvent?.x.toString() + ", " + upMotionEvent?.y.toString()
        return "$downCoords | $upCoords"
    }

}