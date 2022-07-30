package com.github.talkbacktutorial.gestures.data

import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.view.MotionEvent
import kotlin.math.abs

class FlingMotionData {

    private var downMotionEvent: MotionEvent? = null
    private var upMotionEvent: MotionEvent? = null
    private var velocityX: Float = 0.0F
    private var velocityY: Float = 0.0F

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

    fun dataIsValid(): Boolean {
        return downMotionEvent != null && upMotionEvent != null
    }

    fun xVelocity(abs: Boolean): Float {
        return if (abs) abs(velocityX) else velocityX
    }

    fun yVelocity(abs: Boolean): Float {
        return if (abs) abs(velocityY) else velocityY
    }

    fun xOffset(): Float {
        if (dataIsValid()) {
            return upMotionEvent!!.x - downMotionEvent!!.x
        }
        return 0.0F
    }

    fun xDistance(): Float {
        return abs(xOffset())
    }

    fun yOffset(): Float {
        if (dataIsValid()) {
            return upMotionEvent!!.y - downMotionEvent!!.y
        }
        return 0.0F
    }

    fun yDistance(): Float {
        return abs(yOffset())
    }

    fun flingEndsHigher(): Boolean? {
        if (dataIsValid()) {
            return this.yOffset() < 0
        }
        return null
    }

    fun flingEndsFurther(): Boolean? {
        if (dataIsValid()) {
            return this.xOffset() > 0
        }
        return null
    }

    fun downPoint(): PointF? {
        if (dataIsValid()) {
            return PointF(this.downMotionEvent!!.x, this.downMotionEvent!!.y)
        }
        return null
    }

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